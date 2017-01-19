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
            print (text)
            coor = Coordinates(text)
            print(coor.timestamp + "," + str(coor.latitude) + "," + str(coor.longitude) + "\r\n")

    except Exception as ex:
        print (format(ex))
        running = True
