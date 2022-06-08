# https://docs.yugabyte.com/latest/contribute/core-database/build-from-src-ubuntu/
* git clone --branch v2.13.0.1 https://github.com/yugabyte/yugabyte-db.git
* docker buildx build   --platform linux/amd64,linux/arm64 -t olklokk/yugabytedb:latest .