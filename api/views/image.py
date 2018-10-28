import os
import uuid
import datetime

from flask import jsonify, request, current_app

import numpy as np
from PIL import Image as plimage
from flask_classful import FlaskView, route
from marshmallow import ValidationError
from sqlalchemy.exc import IntegrityError
from werkzeug.utils import secure_filename

from models.db_init import db
from models.image import Image
from models.comment import Comment
from schemas.image import ImageSerializer
from schemas.comment import CommentSerializer
from initialize_tensor import run_tensor


class ImagesAPI(FlaskView):
    excluded_methods = ['parse_image']
    route_base = '/images/'

    def index(self):
        page = int(request.args.get('page', 1))
        per_page = 10
        images = Image.query.order_by(Image.posted_at.desc()).paginate(page, per_page, error_out=False)
        result = ImageSerializer.dump(images.items, many=True)
        return jsonify({'images': result,
                        'total': images.total,
                        'per_page': images.per_page,
                        'page': page,
                        'has_next': images.has_next,
                        'has_prev': images.has_prev})

    def get(self, id):
        try:
            image = Image.query.get(id)
        except IntegrityError:
            return jsonify({'message': 'Image could not be found.'}), 400
        result = ImageSerializer.dump(image)
        return jsonify({'image': result})

    def _parse_image(self, image):
        return_data = {}
        unique_filename = str(uuid.uuid4())
        filename = secure_filename(unique_filename + image.filename)
        file_path = os.path.join(current_app.config['UPLOAD_FOLDER'], filename)
        image.save(file_path)
        return_data['image'] = file_path

        im = plimage.open(file_path)
        # convert to thumbnail image
        im.thumbnail((128, 128), plimage.ANTIALIAS)
        thumbnail_filepath = os.path.join(current_app.config['UPLOAD_FOLDER'], "T_" + filename)
        return_data['thumbnail'] = thumbnail_filepath
        im.save(thumbnail_filepath, "JPEG")


        image = plimage.open(file_path).convert('RGB')
        image = image.resize((227, 227), plimage.BILINEAR)
        img_tensor = [np.asarray(image, dtype=np.float32)]
        scores = run_tensor(img_tensor)
        return_data['hasfood'] = scores[0][0]
        return_data['notfood'] = scores[0][1]

        return return_data

    @route('/<id>/comments')
    def comments(self, id):
        comments = Comment.query.filter_by(image_id=id).all()
        result = CommentSerializer.dump(comments, many=True)
        return jsonify({'comments': result})

    @route('/<id>/add_comment', methods=['POST'])
    def add_comment(self, id):
        json_data = {}
        if not request.form.get('text', False):
            return jsonify({'message': 'No comment text provided'}), 400
        else:
            json_data['text'] = request.form.get('text')

        json_data['image_id'] = id

        # Validate and deserialize input
        try:
            CommentSerializer.load(json_data)
        except ValidationError as err:
            return jsonify(err.messages), 422

        comment = Comment(
            image_id=id,
            text=json_data.get('text'),
            posted_at=datetime.datetime.utcnow(),
        )

        db.session.add(comment)
        db.session.commit()
        result = CommentSerializer.dump(Comment.query.get(comment.id))
        return jsonify({
            'message': 'Created new comment.',
            'comment': result,
        })


    def post(self):
        json_data = request.get_json()
        if not json_data:
            json_data = {}

        # Parse the files and add to a location to json_data
        if 'image' not in request.files and 'images' not in request.files:
            return jsonify({'message': 'No image provided'}), 400

        images = []
        results = []
        if 'images' in request.files:
            images = request.files.getlist('images')
        else:
            images.append(request.files.get('image'))

        for request_file in images:
            # if user does not select file, browser also
            # submit an empty part without filename
            if request_file.filename == '':
                return jsonify({'message': 'No image provided'}), 400
            if request_file:
                json_data.update(self._parse_image(request_file))

            # Validate and deserialize input
            try:
                ImageSerializer.load(json_data)
            except ValidationError as err:
                return jsonify(err.messages), 422

            # Create new quote
            image = Image(
                image_location=json_data['image'],
                thumbnail_location=json_data['thumbnail'],
                has_food=json_data['hasfood'],
                not_food=json_data['notfood'],
                posted_at=datetime.datetime.utcnow(),
            )
            db.session.add(image)
            db.session.commit()
            results.append(image.id)
        result = ImageSerializer.dump(Image.query.filter(Image.id.in_(results)), many=True)
        return jsonify({
            'message': 'Created new image.',
            'images': result,
        })