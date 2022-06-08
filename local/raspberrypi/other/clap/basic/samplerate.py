#!/usr/bin/python
import pyaudio

pyaud = pyaudio.PyAudio()
stream = pyaud.open(
    format = pyaudio.paInt16,
    channels = 1,
    rate = 44100,
    input_device_index = 1,
    input = True)

print(stream)