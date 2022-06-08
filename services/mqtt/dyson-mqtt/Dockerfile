
FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
ARG GITLAB_MAVEN_LINOLE_URL
ARG GITLAB_MAVEN_LINOLE_USERNAME
ARG GITLAB_MAVEN_LINOLE_TOKEN
ENV GITLAB_MAVEN_LINOLE_URL=$GITLAB_MAVEN_LINOLE_URL
ENV GITLAB_MAVEN_LINOLE_USERNAME=$GITLAB_MAVEN_LINOLE_USERNAME
ENV GITLAB_MAVEN_LINOLE_TOKEN=$GITLAB_MAVEN_LINOLE_TOKEN
RUN /bin/bash -c "source ./.env && gradle shadowJar --no-daemon"

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/dyson-mqtt.jar
ENTRYPOINT ["java", "-Xms2m", "-Xmx64m", "-jar", "/app/dyson-mqtt.jar"]