FROM homeassistant/home-assistant:latest

RUN /bin/bash -c "sed -i 's/(?:PostgreSQL|EnterpriseDB) /(?:PostgreSQL |EnterpriseDB |CockroachDB CCL v)/g' /usr/local/lib/python3.9/site-packages/sqlalchemy/dialects/postgresql/base.py"
