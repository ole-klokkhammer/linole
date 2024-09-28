FROM hivemq/hivemq-ce:latest

ENV HIVEMQ_EXTENSION_DIR=/opt/hivemq/extensions
ENV HIVEMQ_PLUGINS_DIR=/opt/hivemq/plugins
ENV HIVEMQ_CONF_DIR=/opt/hivemq/conf

RUN mkdir $HIVEMQ_EXTENSION_DIR/hivemq-auth
COPY extensions/hivemq-auth/build/hivemq-extension/hivemq-auth-*.jar $HIVEMQ_EXTENSION_DIR/hivemq-auth/hivemq-auth.jar
COPY extensions/hivemq-auth/build/hivemq-extension/hivemq-extension.xml $HIVEMQ_EXTENSION_DIR/hivemq-auth/hivemq-extension.xml

