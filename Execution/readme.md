# Uitlezen GPS coördinaten

Huidige lokatie: 80°49'07.1"N 103°08'59.9"E

Volledige code:

```ino
#include <SoftwareSerial.h>
 
SoftwareSerial SoftSerial(4, 5);
unsigned char buffer[64];                           // buffer array for data receive over serial port
int count = 0;                                      // counter for buffer array
 
void setup()
{
    SoftSerial.begin(9600);                         // the SoftSerial baud rate
    Serial.begin(9600);                             // the Serial port of Arduino baud rate.
}
 
void loop()
{
    if (SoftSerial.available())                     // if date is coming from software serial port ==> data is coming from SoftSerial shield
    {
        while (SoftSerial.available())              // reading data into char array
        {
            buffer[count++] = SoftSerial.read();    // writing data into array
            if (count == 64) {
                break;
            }
        }
        Serial.write(buffer,count);                 // if no data transmission ends, write buffer to hardware serial port
        clearBufferArray();                         // call clearBufferArray function to clear the stored data from the array
        count = 0;                                  // set counter of while loop to zero
 
 
    }
    if (Serial.available())                         // if data is available on hardware serial port ==> data is coming from PC or notebook
    SoftSerial.write(Serial.read());                // write it to the SoftSerial shield
}
 
void clearBufferArray()                             // function to clear buffer array
{
    for (int i = 0; i < count; i++)
    {
        buffer[i] = NULL;
    }                                               // clear all index of array with command NULL
}
```

Uitgelezen data: 

```none
$GPGSV,3,2,12
$GPGGA,173241.000,8049.1272,N,10308.9925,E,1,10,0.98,-4.0,M,47.,,A*64
$GPGGA,173243.000,8049.1272,N,10308.9925,E,1,10,0.98,-4.
$GPGGA,173244.000,8049.1272,N,10308.9925,E,1,10,0.98,-4.0,M,47.
$GPGGA,173246.000,8049.1272,N,10308.9925,E,1,10,0.98,-4.0,M,47.17,05,62,213,25,13,55,287,26,28,39,132,31*78
```

Kaart met details over coördinaten:

![image](https://cloud.githubusercontent.com/assets/16222780/20721479/2d8458ee-b663-11e6-819f-945a4de08301.png)
