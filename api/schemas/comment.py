from marshmallow import Schema, fields

from models.db_init import marshmall
from models.comment import Comment


class CommentSchema(marshmall.ModelSchema):
    class Meta:
        model = Comment


CommentSerializer = CommentSchema()

