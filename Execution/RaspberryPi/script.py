import serial
import json

ser = serial.Serial('/dev/ttyUSB0', 9600)
counter = 0

class Coordinates(object):

    def __init__(self, j):
        self.__dict__ = json.loads(j)
    
    def decdeg2dms(dd):
        mnt,sec = divmod(dd*3600,60)
        deg,mnt = divmod(mnt,60)
        return deg,mnt,sec

while counter <= 100:
    text = ser.readline()
    print text

    try:
        coor = Coordinates(text)
        
        print "uur: " + str(coor.uur)
        print "lat: " + str(coor.lat) + str(coor.latChar)
        print "lon: " + str(coor.lon) + str(coor.lonChar)
    
    except ValueError:
        print "Error in code"

    counter += 1