from marshmallow import Schema, fields

from models.db_init import marshmall
from models.image import Image


class ImageSchema(marshmall.ModelSchema):
    comments = marshmall.List(marshmall.HyperlinkRelated('CommentsView:get'))
    class Meta:
        model = Image


ImageSerializer = ImageSchema()

