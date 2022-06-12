#!/bin/bash

docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/dyson-mqtt:1.0.0 .
docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/dyson-mqtt:latest .
