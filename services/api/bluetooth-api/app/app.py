from flask import Flask
from flask_restful import Resource, Api
from resources.airthings import Airthings
from resources.paxcalima import PaxCalima
from resources.oralb import OralB
from config import env_config
from os import getenv

environment = getenv("FLASK_ENV", "production")
app = Flask(__name__)
app.config.from_object(env_config[environment])
api = Api(app)


class DefaultResource(Resource):
    def get(self):
        return True


api.add_resource(DefaultResource, '/')
api.add_resource(Airthings, '/airthings')
api.add_resource(PaxCalima, '/paxcalima')
api.add_resource(OralB, '/oralb')

if __name__ == '__main__':
    app.run(debug=debug, host='0.0.0.0', port=port)
