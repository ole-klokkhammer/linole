#!/bin/sh
CONFIG_DIR="$HOME/.FlashPrint"
MODEL_DIR="/home/ole/workspace/oleklokkhammer/projects/3d-printing/"

# You may want to restrict network access
#docker run --rm --name flashprint --network=none \
docker run --rm --name flashprint --network=host \
	-v /tmp/.X11-unix:/tmp/.X11-unix \
	-v "$CONFIG_DIR:/home/flashprint/.FlashPrint" \
	-v "$MODEL_DIR:/home/flashprint/3dmodels" \
	--privileged \
	-v /dev/bus/usb:/dev/bus/usb \
	-v /dev/dri:/dev/dri:rw \
	-e DISPLAY \
	"${@}" \
	ttytyper/flashprint &
