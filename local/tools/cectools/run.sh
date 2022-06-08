#!/bin/bash

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/vc/lib
chmod a+rw /dev/vchiq
node dist/index.js
