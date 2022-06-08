# 
* https://github.com/cockroachdb/cockroach/tree/master/build
* git clone --branch v21.2.7 https://github.com/cockroachdb/cockroach
* cd ./cockroach
* ./build/builder.sh mkrelease linux-gnu
* ./build/builder.sh mkrelease arm64-linux-gnu
* cp ./cockroach-linux-2.6.32-gnu-amd64 build/deploy/cockroach-amd64
* cp ./cockroach-linux-3.7.10-gnu-aarch64 build/deploy/cockroach-arm64
* cp ./lib.docker_amd64/libgeos_c.so ./lib.docker_amd64/libgeos.so build/deploy
* cp -r licenses build/deploy  

* cd build/deploy 
* modify Dockerfile to match the one in the folder of this readme
* docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/cockroachdb:21.2.9 .
* docker buildx build --push --platform linux/amd64,linux/arm64 -t olklokk/cockroachdb:latest .
* delete https://github.com/cockroachdb/cockroach