import lgpio
import os
import sys
import smbus
import time

#GPIO.setmode(GPIO.BCM)

pulseStart = 0.0
SHUTDOWN = 24               #pin 18
ATX_WATCHDOG_ADDRESS = 0x5A #I2C address
BOOT_OK_COMMAND = 0x83      #Set Boot Ok process
BOOT_OK = 0x01              #Signal that we booted up okay
REBOOTPULSEMINIMUM = 0.2    #reboot pulse signal should be at least this long
REBOOTPULSEMAXIMUM = 0.6    #reboot pulse signal should be at most this long

print ("=====================================")
print ("== ATX-PSU_startup: Initializing GPIO")

chip = lgpio.gpiochip_open(0)
lgpio.gpio_claim_output(chip, SHUTDOWN)

#GPIO.setup(SHUTDOWN, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)

try:
    print ("== Signalling Boot Ok")
    i2c = lgpio.i2c_open(1, ATX_WATCHDOG_ADDRESS)
    lgpio.i2c_write_byte(i2c, BOOT_OK_COMMAND)
    lgpio.i2c_write_byte(i2c, BOOT_OK)
    lgpio.i2c_close(i2c)

    while True:
        print ("== Waiting for shutdown pulse")
        if (lgGpioRead(chip, SHUTDOWN) == 1) {
        }
        GPIO.wait_for_edge(SHUTDOWN, GPIO.RISING)

        print ("shutdown pulse received")
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