from marshmallow import Schema, fields

from models.db_init import marshmall
from models.image import Image
from schemas.comment import CommentSchema


class ImageSchema(marshmall.ModelSchema):
    # comments = marshmall.List(marshmall.HyperlinkRelated('CommentsRetrievalAPI:get'))
    comments = marshmall.List(marshmall.Nested(CommentSchema))
    class Meta:
        model = Image


ImageSerializer = ImageSchema()

