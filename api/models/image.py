from db_init import db


class Image(db.Model):
    '''
    Model to store basic image information
    '''
    id = db.Column(db.Integer, primary_key=True)
    image_location = db.Column(db.String(200))
    thumbnail_location = db.Column(db.String(200))
    has_food = db.Column(db.Float)
    not_food = db.Column(db.Float)
    comments = db.relationship('Comment', backref='image', lazy=True)
    posted_at = db.Column(db.DateTime)