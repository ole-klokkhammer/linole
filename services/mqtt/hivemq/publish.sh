#!/bin/bash

cd extensions/hivemq-auth && ./gradlew clean build hivemqExtensionZip
cd .. && cd ..
docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/hivemq:latest .