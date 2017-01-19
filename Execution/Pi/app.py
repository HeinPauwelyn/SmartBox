import serial
import json
import urllib2

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
            data = Coordinates(text)
            # print(coor.timestamp + "," + str(coor.latitude) + "," + str(coor.longitude) + "\r\n")
            
            req = urllib2.Request('http://192.168.0.187:3000/locations/add')
            req.add_header('Content-Type', 'application/json')
            
            response = urllib2.urlopen(req, text)
            print (response)
    except Exception as ex:
        print (format(ex))
        running = True
