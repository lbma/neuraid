# For use with monkeyrunnner in the android-sdk/tools directory

# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
import time

# Time between screenshots
SLEEPTIME = 3

# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()

# Record the previous time
pTime = time.time()

while True:
    # Takes a screenshot
    result = device.takeSnapshot()

    # What time is it now?
    now = time.time()

    # Writes the screenshot to a file
    result.writeToFile(str(int(now)) + ".png",'png')

    # Determine how long it took to take the screenshot
    wait = SLEEPTIME - (now - pTime)
    pTime = now

    # if it took longer than SLEEPTIME seconds for a screenshot
    # immediately take another, otherwise sleep
    print("Sleep for " + str(wait))
    time.sleep(max(0, wait))

