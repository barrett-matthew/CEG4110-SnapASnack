from marshmallow import Schema, fields


class ImageSchema(Schema):
    id = fields.Int(dump_only=True)
    # comments = fields.Nested(CommentSchema, dump_only=True)
    image = fields.Str(required=True)
    hasfood = fields.Float(required=True)
    notfood = fields.Float(required=True)
    posted_at = fields.DateTime(dump_only=True)


image_schema = ImageSchema()
images_schema = ImageSchema(many=True, only=('id', 'image', 'hasfood', 'notfood', 'posted_at'))