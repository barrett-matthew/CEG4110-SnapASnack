import os
from flask import Flask, Response

from flask_migrate import Migrate

from models.db_init import db, marshmall
from views.image import ImagesAPI
from views.comment import CommentsRetrievalAPI

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

ImagesAPI.register(app)
CommentsRetrievalAPI.register(app)

def root_dir():
    return os.path.abspath(os.path.dirname(__file__))

def get_file(filename):
    try:
        src = os.path.join(root_dir(), filename)
        return open(src).read()
    except IOError as exc:
        return str(exc)


@app.route('/', methods=['GET'])
def metrics():  # pragma: no cover
    content = get_file('static/swagger/index.html')
    return Response(content, mimetype="text/html")

if __name__ == '__main__':
    app.run()
