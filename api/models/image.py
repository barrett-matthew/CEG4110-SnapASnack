from db_init import db
from sqlalchemy.ext.hybrid import hybrid_property

from models.comment import Comment

class Image(db.Model):
    '''
    Model to store basic image information
    '''
    id = db.Column(db.Integer, primary_key=True)
    image_location = db.Column(db.String(200))
    thumbnail_location = db.Column(db.String(200))
    has_food = db.Column(db.Float)
    not_food = db.Column(db.Float)
    comments = db.relationship('Comment', backref='image')
    posted_at = db.Column(db.DateTime)

    @hybrid_property
    def num_comments(self):
        return db.session.query(db.func.count(Comment.id)).filter(Comment.image_id == self.id)

    @num_comments.expression
    def _num_comments_expression(cls):
        return (db.select([db.func.count(Comment.id).label("num_comments")])
                .where(Comment.image_id == cls.id)
                .label("total_comments")
                )