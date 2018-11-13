from flask import jsonify

from flask_classful import FlaskView
from sqlalchemy.exc import IntegrityError

from models.comment import Comment
from schemas.comment import CommentSerializer


class CommentsRetrievalAPI(FlaskView):
    route_base = '/comments/'

    def get(self, id):
        """
        Retrieve a Comment
        ---
        tags:
          - comments
        parameters:
          - in: path
            name: id
            description: ID of the comment to be retrieved
            required: true
            type: string
        responses:
          201:
            description: User created
          400:
        """
        try:
            image = Comment.query.get(id)
        except IntegrityError:
            return jsonify({'message': 'Comment could not be found.'}), 400
        result = CommentSerializer.dump(image).data
        return jsonify({'comment': result})
