from flask import Flask

from models.db_init import db
from views.image import ImagesView

UPLOAD_FOLDER = './static/images'

app = Flask(__name__)
app.config['DEBUG'] = True
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///flask.db'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.app_context().push()

db.init_app(app)
# create these if models if they don't exist
db.create_all()

ImagesView.register(app)


if __name__ == '__main__':
    app.run()
