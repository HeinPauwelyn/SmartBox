import serial
import json

ser = serial.Serial('/dev/ttyUSB0', 9600)
counter = 0


class Coordinates(object):

    def __init__(self, j):
        self.__dict__ = json.loads(j)

while counter <= 100:
    text = ser.readline()

    coor = Coordinates(text)

    print "lat: " + str(coor.lat)
    print "long: " + str(coor.long)
    counter += 1