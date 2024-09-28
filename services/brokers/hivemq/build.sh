#!/bin/bash

docker buildx build --push --platform linux/amd64,linux/arm64 --build-arg GITHUB_USERNAME=$GITHUB_USERNAME --build-arg GITHUB_TOKEN=GITHUB_TOKEN -t olklokk/hivemq:latest .