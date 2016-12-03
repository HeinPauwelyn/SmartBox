import serial
import json

running = True;

try:
    ser = serial.Serial('/dev/ttyUSB0', 9600)
except SerialException:
    print "ttyUSB0 not found!"

class Coordinates(object):

    def __init__(self, j):
        self.__dict__ = json.loads(j)
    
while running:
    try:
        text = ser.readline()
        coor = Coordinates(text)
        
        # print "uur: " + str(coor.uur)
        # print "lat: " + str(coor.lat) + str(coor.latChar)
        # print "lon: " + str(coor.lon) + str(coor.lonChar)

        with open('locations.csv', 'a') as file:
            file.write(coor.uur + "," + str(coor.lat) + coor.latChar + "," + coor.lon + coor.lonChar + "\r\n")
        
        print "data printed"

    except SerialException:
        running = False

    except ValueError:
        print "Error in code"