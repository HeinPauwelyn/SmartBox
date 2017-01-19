import serial
import json

running = True

try:
    ser = serial.Serial('/dev/ttyUSB0', 9600)
except SerialException:
    print("ttyUSB0 not found!")

class Coordinates(object):

    def __init__(self, j):
        self.__dict__ = json.loads(j)
    
while running:
    try:
        text = ser.readline()
        if type(text) == str:
            coor = Coordinates(text)
            print(text)

    except SerialException:
        running = False