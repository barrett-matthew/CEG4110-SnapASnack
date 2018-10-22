from flask import jsonify

from flask_classful import FlaskView
from sqlalchemy.exc import IntegrityError

from models.comment import Comment
from schemas.comment import CommentSerializer


class CommentsView(FlaskView):

    def get(self, id):
        try:
            image = Comment.query.get(id)
        except IntegrityError:
            return jsonify({'message': 'Comment could not be found.'}), 400
        result = CommentSerializer.dump(image)
        return jsonify({'comment': result})