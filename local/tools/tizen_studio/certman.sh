#!/bin/bash 

CONTAINER_NAME=tizen-studio-ide
TIZEN_USER=tizen
TIZEN_DIR=/home/${TIZEN_USER}/tizen-studio/  

docker exec -u ${TIZEN_USER} -d ${CONTAINER_NAME} bash -c "cd /home/${TIZEN_USER} && ${TIZEN_DIR}tools/certificate-manager/certificate-manager"
