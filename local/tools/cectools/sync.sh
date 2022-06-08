#!/bin/bash

# rsync -av -e ssh --exclude='node_modules' . volumio@192.168.1.85:~/workspace/cectools
rsync -av -e ssh . volumio@192.168.1.85:~/workspace/cectools
