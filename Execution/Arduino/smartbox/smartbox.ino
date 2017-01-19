#include <../SoftwareSerial.h>
int SERIAL_BAUD = 9600;

SoftwareSerial GPSSoftSerial(4, 5);
unsigned char buffer[64];
int count = 0;
int loopCounter = 0;

void setup () 
{
    GPSSoftSerial.begin(SERIAL_BAUD);
    Serial.begin(SERIAL_BAUD); 
}

void loop()
{
    if (loopCounter++ == 100) 
    {
        loopCounter = 0;
        
        readGPSData();
    }
}

void readGPSData()
{
    bool gotGPGGA = false;
    int teller = 0;
    String text, json, uur;
    float lon, lat, alt;

    if(!GPSSoftSerial.available()) {
        GPSSoftSerial.begin(SERIAL_BAUD);
    }

    if (GPSSoftSerial.available()) {
                
        while (GPSSoftSerial.available()) {
        // while(teller < 20) {
            char read = GPSSoftSerial.read();

            // Serial.print(read);
            if (String(read) == ",") {

                if (text == "$GPGGA") {
                    gotGPGGA = true;
                }

                if (gotGPGGA) {
                  
                    switch (teller) {
                        case 1:
                            uur = text.substring(0, 2) + ":" + text.substring(2, 4) + ":" + text.substring(4, 6);
                            //uur = text.toInt();
                            // Serial.print("uur: " + String(uur));
                            // GPSSensor.setTimestamp(uur);
                            break;
                        case 2:
                            lat = text.toFloat();

                            // Serial.print("lat: " + String(text));
                            break;
                        case 3:
                            if (text == "S") {
                                lat = -lat;
                            }

                            // Serial.print(" lat: " + String(lat));
                            // gpsSensor.setLatitude(lat);
                            break;
                        case 4:
                            lon = text.toFloat();
                            
                            // Serial.print("lon: " + String(text));
                            break;
                        case 5:
                            if (text == "W") {
                                lon = -lon;
                            }

                            // Serial.print(" lon: " + String(lon));
                            // gpsSensor.setLongitude(lon);
                            break;
                    }

                    teller += 1;
                }
                
                text = "";
            }
            else {
                text += read;
            }
            
            if (teller == 10) {
                json = "{\"latitude\": " + String(lat) + ", \"longitude\": " + String(lon) + ", \"altidude\": 0, \"timestamp\": \"" + uur + "\" }";
                Serial.println(json);
                
                gotGPGGA = false;
                break;
            }
        }
    }

    // Serial.println("");
    // dumpSendResult(gpsSens);
}
