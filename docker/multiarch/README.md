# Example
* qemu-user-static
* binfmt-all-static...
* docker buildx create --config buildkitd.toml --name multiarch-insecure-registry --use --driver-opt network=host
* docker buildx build --push --platform linux/amd64,linux/arm64 -t 192.168.0.203:5000/<image>:<tag> .