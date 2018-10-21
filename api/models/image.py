from db_init import db

class Image(db.Model):
    '''
    Model to store basic image information
    '''
    id = db.Column(db.Integer, primary_key=True)
    image = db.Column(db.String(200))
    hasfood = db.column(db.Float)
    notfood = db.column(db.Float)
    comments = db.relationship('Comment', backref='image', lazy=True)
    posted_at = db.Column(db.DateTime)