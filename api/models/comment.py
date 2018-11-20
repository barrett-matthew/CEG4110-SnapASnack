from db_init import db

class Comment(db.Model):
    '''
    model to store associated comments
    '''
    id = db.Column(db.Integer, primary_key=True)
    text = db.Column(db.String(1000))
    image_id = db.Column(db.Integer, db.ForeignKey('image.id'), nullable=False)
    posted_at = db.Column(db.DateTime)