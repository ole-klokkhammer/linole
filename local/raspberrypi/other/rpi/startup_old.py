import RPi.GPIO as GPIO
import os
import sys
import smbus
import time

GPIO.setmode(GPIO.BCM)
pulseStart = 0.0
SHUTDOWN = 24               #pin 18
ATX_WATCHDOG_ADDRESS = 0x5A #I2C address
BOOT_OK_COMMAND = 0x83      #Set Boot Ok process
BOOT_OK = 0x01              #Signal that we booted up okay
REBOOTPULSEMINIMUM = 0.2    #reboot pulse signal should be at least this long
REBOOTPULSEMAXIMUM = 0.6    #reboot pulse signal should be at most this long
print ("\n=====================================\n")
print ("== ATX-PSU_startup: Initializing GPIO")
GPIO.setup(SHUTDOWN, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)
try:
    print ("\n== Signalling Boot Ok\n")
    bus = smbus.SMBus(1)
    bus.write_byte_data(ATX_WATCHDOG_ADDRESS, BOOT_OK_COMMAND, BOOT_OK)
    bus.close()
    while True:
        print ("\n== Waiting for shutdown pulse\n")
        GPIO.wait_for_edge(SHUTDOWN, GPIO.RISING)
        print ("\nshutdown pulse received\n")
        pulseValue = GPIO.input(SHUTDOWN)
        pulseStart = time.time()
        pinResult = GPIO.wait_for_edge(SHUTDOWN, GPIO.FALLING, timeout = 600)
        if pinResult == None:
            os.system("sudo poweroff")
            sys.exit()
        elif time.time() - pulseStart >= REBOOTPULSEMINIMUM:
            os.system("sudo reboot")
            sys.exit()
        if GPIO.input(SHUTDOWN):
            GPIO.wait_for_edge(SHUTDOWN, GPIO.FALLING)
except:
    pass
finally:
    GPIO.cleanup()