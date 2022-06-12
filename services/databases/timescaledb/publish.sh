#!/bin/bash

docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/timescaledb-ha:1.4.0 ./timescaledb-docker-ha
docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/timescaledb-ha:latest ./timescaledb-docker-ha