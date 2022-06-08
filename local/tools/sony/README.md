
# HOW TO


## quickstart
curl -i -d '{"method": "setPowerSettings","id": 55,"params": [{"settings":[{"target":"quickStartMode","value":"on"}]}],"version": "1.0"}' http://192.168.10.34:10000/sony/system


## WOL
curl -i -d '{"method": "setPowerSettings","id": 55,"params": [{"settings":[{"target":"wolMode","value":"off"}]}],"version": "1.0"}' http://192.168.10.34:10000/sony/system
