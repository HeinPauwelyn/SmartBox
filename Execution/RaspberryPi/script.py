import serial
import json

running = True;

try:
    ser = serial.Serial('/dev/ttyUSB0', 9600)
except:
    print "ttyUSB0 not found!"

class Coordinates(object):

    def __init__(self, j):
        self.__dict__ = json.loads(j)
    
while running:
    try:
        text = ser.readline()
        coor = Coordinates(text)
        
        with open('locations.csv', 'a') as file:
            file.write(coor.uur + "," + str(coor.lat) + coor.latChar + "," + coor.lon + coor.lonChar + "\r\n")
        
        print "data printed"

    except ValueError:
        print "Error in code"
    
    except:
        running = False