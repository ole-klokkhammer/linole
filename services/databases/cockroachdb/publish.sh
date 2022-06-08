#!/bin/bash

docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/cockroachdb:21.1.16 .
docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/cockroachdb:latest .