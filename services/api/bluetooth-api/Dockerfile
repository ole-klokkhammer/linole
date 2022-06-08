FROM python:2.7

RUN apt update
RUN apt install -y python-pip libglib2.0-dev
RUN pip2 install tableprint==0.8.0 bluepy==1.2.0 flask flask_restful gunicorn futures

ADD ./app /app
WORKDIR /app

ENV FLASK_APP=bluetooth
ENV FLASK_ENV=production

ENTRYPOINT ["gunicorn", "--config", "gunicorn_config.py", "wsgi:app"]