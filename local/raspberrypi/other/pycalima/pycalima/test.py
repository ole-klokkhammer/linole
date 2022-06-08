from pycalima.Calima import Calima
import sys
import time 

boostsecs=10

fan = Calima("58:2B:DB:34:21:4D", "33394119") 
print("Setting Boost mode for {} seconds".format(boostsecs))
fan.setBoostMode(1,2400,boostsecs)
time.sleep(2)
print(fan.getBoostMode())
fan.disconnect()

