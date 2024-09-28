import paho.mqtt.client as mqtt
import requests
from os import getenv
import schedule
import time

HOST = getenv('HOST', '192.168.0.201')
PORT = getenv('PORT', 1883)
KEEPALIVE = getenv('KEEPALIVE', 60)


def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))


def on_publish(client, userdata, result):
    print("data published \n")
    pass


client = mqtt.Client()
client.on_connect = on_connect
client.on_publish = on_publish
client.connect(HOST, PORT, KEEPALIVE)
client.loop_start()

bluetoothApiUrl = 'http://bluetooth-api.linole:8000'


def airthings():
    try:
        response = requests.get(bluetoothApiUrl + '/airthings?pin=2930018919')
        data = response.json()
        client.publish('bluetooth/airthings', payload=str(data), qos=2, retain=True)
        client.publish('bluetooth/airthings/co2', payload=str(data['co2']), qos=2, retain=True)
        client.publish('bluetooth/airthings/temperature', payload=str(data['temperature']), qos=2, retain=True)
        client.publish('bluetooth/airthings/voc', payload=str(data['voc']), qos=2, retain=True)
        client.publish('bluetooth/airthings/humidity', payload=str(data['humidity']), qos=2, retain=True)
        client.publish('bluetooth/airthings/pressure', payload=str(data['pressure']), qos=2, retain=True)
        client.publish('bluetooth/airthings/radon_lt_avg', payload=str(data['radon_lt_avg']), qos=2, retain=True)
        client.publish('bluetooth/airthings/radon_st_avg', payload=str(data['radon_st_avg']), qos=2, retain=True)
    except Exception as e:
        print(e)


def oralb():
    try:
        response = requests.get(bluetoothApiUrl + '/oralb?mac=10:CE:A9:2A:41:F6')
        data = response.json()
        client.publish('bluetooth/oralb', payload=str(data), qos=2, retain=True)
    except Exception as e:
        print(e)


def paxcalima():
    try:
        response = requests.get(bluetoothApiUrl + '/paxcalima?mac=58:2B:DB:34:21:4D&pin=33394119')
        data = response.json()
        client.publish('bluetooth/paxcalima', payload=str(data), qos=2, retain=True)
    except Exception as e:
        print(e)


print("Scheduling jobs ...")
schedule.every(15).minutes.do(airthings)
schedule.every(1).minutes.do(oralb)
schedule.every(5).minutes.do(paxcalima)
while True:
    schedule.run_pending()
    time.sleep(1)
