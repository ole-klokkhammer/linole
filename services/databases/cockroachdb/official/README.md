# 
* https://github.com/cockroachdb/cockroach/tree/master/build
* https://wiki.crdb.io/wiki/spaces/CRDB/pages/181338446/Getting+and+building+CockroachDB+from+source
* mkdir -p $(go env GOPATH)/src/github.com/cockroachdb
* cd $(go env GOPATH)/src/github.com/cockroachdb
* git clone --branch v22.1.5 https://github.com/cockroachdb/cockroach
* cd cockroach
* touch .bazelrc.user
* echo "build --config nolintonbuild --config crosslinux" >> .bazelrc.user
* echo "build --remote_cache=http://127.0.0.1:9867" >> ~/.bazelrc
* ./dev doctor
* ./dev build
* ./dev build --cross=linuxarm --cross=linux
* cp ./_bazel/bin/pkg/cmd/cockroach/cockroach_/cockroach build/deploy/cockroach-amd64
* cp ./artifacts/cockroach build/deploy/cockroach-arm64
* cp ./lib.docker_amd64/libgeos_c.so ./lib.docker_amd64/libgeos.so build/deploy
* cp -r licenses build/deploy
* 
# older make build (deprecated)
* cd /home/ole/go
* git clone --branch v22.1.5  https://github.com/cockroachdb/cockroach
* cd ./cockroach
* ./build/builder.sh mkrelease amd64-linux-gnu
* ./build/builder.sh mkrelease arm64-linux-gnu
* cp ./cockroach-linux-2.6.32-gnu-amd64 build/deploy/cockroach-amd64
* cp ./cockroach-linux-3.7.10-gnu-aarch64 build/deploy/cockroach-arm64
* cp ./lib.docker_amd64/libgeos_c.so ./lib.docker_amd64/libgeos.so build/deploy
* cp -r licenses build/deploy  
* scp -r ubuntu@192.168.10.100/home/ole/.... 
* modify Dockerfile to match the one in the folder of this readme
* docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/cockroachdb:22.2.5 .
* docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/cockroachdb:latest .
* delete https://github.com/cockroachdb/cockroach

