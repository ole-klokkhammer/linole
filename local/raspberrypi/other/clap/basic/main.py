#!/usr/bin/python
import pyaudio
import sys
import _thread as thread
from array import array
from time import sleep

clap = 0 	

def toggleLight(): 
	try:
		url = "https://linole.duckdns.org:6102/api/services/light/toggle"
		headers = {
			"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiMDc4YTFhZGQwZmI0ZGNhYWRkNzBjMTlmMGVkNjQ0NCIsImlhdCI6MTU5NDMxNjEyNywiZXhwIjoxOTA5Njc2MTI3fQ.6RFGvRb-O2FBPhhXNGdAxhfGPILEYgt5UZBNveehjXI",
			"content-type": "application/json",
		}
		payload = {'entity_id': 'light.kitchen'} 
		response = requests.post(url, headers=headers, json=payload)
		print(response.text) 
	except:
	  print("An exception occurred")


def main():
	global clap

	chunk = 512
	FORMAT = pyaudio.paInt16
	CHANNELS = 1
	RATE = 44100
	threshold = 1000
	p = pyaudio.PyAudio()
	stream = p.open(format=FORMAT,
					channels=CHANNELS, 
					rate=RATE,
					input=True,
					input_device_index=1,
					frames_per_buffer=chunk)
	try:
		print("Clap detection initialized")
		while True:
			data = stream.read(chunk, exception_on_overflow=False)
			as_ints = array('h', data)
			max_value = max(as_ints)
			if max_value > threshold:
				clap += 1
				print("Clapped")
				sleep(0.05)
			if clap == 2:
				print("Clapping Ended")
				clap = 0
				toggleLight()
	except (KeyboardInterrupt, SystemExit):
		print("\rExiting")
		stream.stop_stream()
		stream.close()
		p.terminate()

if __name__ == '__main__':
	main()