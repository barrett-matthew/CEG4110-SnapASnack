from flask import Flask

from flask_migrate import Migrate

from models.db_init import db, marshmall
from views.image import ImagesView
from views.comment import CommentsView

UPLOAD_FOLDER = './static/images'

app = Flask(__name__)
app.config['DEBUG'] = True
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///flask.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.app_context().push()

db.init_app(app)
migrate = Migrate(app, db)
marshmall.init_app(app)

ImagesView.register(app)
CommentsView.register(app)


if __name__ == '__main__':
    app.run()
