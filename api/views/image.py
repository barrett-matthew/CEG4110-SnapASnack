import os
import uuid
import datetime

from flask import jsonify, request, current_app

import numpy as np
from PIL import Image as plimage
from flask_classful import FlaskView
from marshmallow import ValidationError
from sqlalchemy.exc import IntegrityError
from werkzeug.utils import secure_filename

from models.db_init import db
from models.image import Image
from models.comment import Comment # hack till I create a comment view
from schemas.image import images_schema, image_schema
from initialize_tensor import run_tensor


class ImagesView(FlaskView):
    def index(self):
        images = Image.query.all()
        result = images_schema.dump(images, many=True)
        return jsonify({'images': result})

    def get(self, id):
        try:
            image = Image.query.get(id)
        except IntegrityError:
            return jsonify({'message': 'Image could not be found.'}), 400
        result = image_schema.dump(image)
        return jsonify({'image': result})

    def post(self):
        json_data = request.get_json()
        if not json_data:
            json_data = {}

        # Parse the files and add to a location to json_data
        if 'image' not in request.files:
            return jsonify({'message': 'No image provided'}), 400

        request_file = request.files['image']
        # if user does not select file, browser also
        # submit an empty part without filename
        if request_file.filename == '':
            return jsonify({'message': 'No image provided'}), 400
        if request_file:
            unique_filename = str(uuid.uuid4())
            filename = secure_filename(unique_filename + request_file.filename)
            file_path = os.path.join(current_app.config['UPLOAD_FOLDER'], filename)
            request_file.save(file_path)
            json_data['image'] = file_path
            image = plimage.open(file_path).convert('RGB')
            image = image.resize((227, 227), plimage.BILINEAR)
            img_tensor = [np.asarray(image, dtype=np.float32)]
            scores = run_tensor(img_tensor)
            json_data['hasfood'] = scores[0][0]
            json_data['notfood'] = scores[0][1]

        # Validate and deserialize input
        try:
            data = image_schema.load(json_data)
        except ValidationError as err:
            return jsonify(err.messages), 422

        # Create new quote
        image = Image(
            image=json_data['image'],
            hasfood=json_data['hasfood'],
            notfood=json_data['notfood'],
            posted_at=datetime.datetime.utcnow(),
        )
        db.session.add(image)
        db.session.commit()
        result = image_schema.dump(Image.query.get(image.id))
        return jsonify({
            'message': 'Created new image.',
            'quote': result,
        })