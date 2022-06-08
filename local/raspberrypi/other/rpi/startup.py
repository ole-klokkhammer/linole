import lgpio
import os
import sys
import time

pulseStart = 0.0
SHUTDOWN = 24  # pin 18
ATX_WATCHDOG_ADDRESS = 0x5A  # I2C address
BOOT_OK_COMMAND = 0x83  # Set Boot Ok process
BOOT_OK = 0x01  # Signal that we booted up okay
REBOOTPULSEMINIMUM = 0.2  # reboot pulse signal should be at least this long
REBOOTPULSEMAXIMUM = 0.6  # reboot pulse signal should be at most this long

print("=====================================")
print("== ATX-PSU_startup: Initializing GPIO")

chip = lgpio.gpiochip_open(0)
lgpio.gpio_claim_input(chip, SHUTDOWN)
lgpio.exceptions = True

try:
    print("== Signalling Boot Ok")
    i2c = lgpio.i2c_open(1, ATX_WATCHDOG_ADDRESS)
    lgpio.i2c_write_byte(i2c, BOOT_OK_COMMAND)
    print("== BOOT_OK_COMMAND")
    time.sleep(1)
    lgpio.i2c_write_byte(i2c, BOOT_OK)
    print("== BOOT_OK")
    time.sleep(1)


    def cb_falling(_chip, gpio, level, timestamp):
        print(_chip, gpio, level, timestamp)
        if level is None:
            os.system("sudo poweroff")
            sys.exit()
        elif time.time() - pulseStart >= REBOOTPULSEMINIMUM:
            os.system("sudo reboot")
            sys.exit()


    def cb_rising(_chip, gpio, level, timestamp):
        print(_chip, gpio, level, timestamp)
        print("Shutdown pulse received")
        pulseStart = time.time()


    print("== Waiting for shutdown pulse")
    print("== Adding callback for RISING_EDGE")
    rising_callback = lgpio.callback(chip, SHUTDOWN, lgpio.RISING_EDGE, cb_rising)
    falling_callback = lgpio.callback(chip, SHUTDOWN, lgpio.FALLING_EDGE, cb_falling)

except:
    print("Something went wrong")
    pass
