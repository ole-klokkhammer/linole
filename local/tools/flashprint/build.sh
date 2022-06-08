#!/bin/sh
docker build --network=host -t ttytyper/flashprint "$(dirname "$0")" "${@}"
