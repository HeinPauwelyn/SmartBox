/**********************************/
/* Declaraties en imports         */
/**********************************/

#include "keys.h"
#include "device.h"
#include "LowPower.h"
#include "instrumentationParamEnum.h"
#include "Sensor.h"
#include "sensor.h"
#include "LoRaModemMicrochip.h"
#include <SoftwareSerial.h>
#include <avr/wdt.h>
#include <avr/sleep.h>

#define SERIAL_BAUD 9600
#define debugSerial Serial
#define BTN_SEND_PIN 2
#define IRQ 0
#define PIN_TX_RN2483 3
#define PIN_RX_RN2483 NULL
#define PIN_PWR_RN2483 23
#define PIN_Q_MT 4
#define PIN_Q_FULL 20
#define PIN_Q_IN_BETWEEN 21
#define MODEM_SERIAL Serial1

extern volatile unsigned long timer0_millis;
volatile boolean canSendFromQueue = false;
volatile unsigned long lastMsgSent = millis();
volatile boolean sendGPS = true;
volatile byte delayCnt = 0;
volatile byte sensorSelect = 0;

unsigned long new_value = 0;
bool connection = true;
int value = 0;

LoRaModemMicrochip modem(&MODEM_SERIAL, &debugSerial);
Device libTest(&modem, &debugSerial);
GPSSensor gpsSensor;
EnCoSensor enCoSensor;
SoftwareSerial SoftSerial(4, 5);
unsigned char buffer[64];
int count = 0;

/**********************************/
/* Sensoren                       */
/**********************************/

void sendSensor(String sensorSelect = "gps") {
    if (sensorSelect == "gps") {
        sendGPSData();
    }
    else if (sensorSelect == "battery") {
        sendBatteryLevel();
    }
}

void sendBatteryLevel() {
    BatteryLevel iSens(666);
    dumpSendResult(iSens);
}

void sendGPSData() {
    bool gotGPGGA = false;
    int teller = 0;
    String text, json, uur;
    float lon, lat, alt;

    if (SoftSerial.available()) {

        while (SoftSerial.available()) {
            char read = SoftSerial.read();

            buffer[count++] = read;

            if (String(read) == ",") {

                if (text == "$GPGGA") {
                    gotGPGGA = true;
                }

                if (gotGPGGA) {
                    switch (teller) {
                        case 1:
                            uur = text.substring(0, 2) + ":" + text.substring(2, 4) + ":" + text.substring(4, 6);
                            json += "{ \"uur\": \"" + uur + "\", ";
                            break;
                        case 2:
                            lat = text.toFloat();
                            json += "\"lat\": \"" + text + "\", ";
                            break;
                        case 3:
                            json += "\"latChar\": \"" + text + "\", ";
                            break;
                        case 4:
                            lon = text.toFloat();
                            json += "\"lon\": \"" + text + "\", ";
                            break;
                        case 5:
                            json += "\"lonChar\": \"" + text + "\" }";
                            break;
                    }

                    teller += 1;
                }
                text = "";
            }
            else {
                text += read;
            }

            if (count == 64) {
                if (json != "") {
                    Serial.println(json);
                    
                    GPSSensor gpsSens(lon, lat, alt, 0);
                    
                    dumpSendResult(gpsSens);
                }
                break;
            }
        }

        delay(2000);
        clearBufferArray();
        count = 0;
    }
}

void clearBufferArray() {
    for (int i = 0; i < count; i++) {
        buffer[i] = NULL;
    }
}

void handleInput() {
    //Get the input string
    String input = Serial.readString();

    //Remove any whitespace or CR/LF
    input.trim();

    //Echo the input
    Serial.println("Command: " + input + " received");

    //Process the input
    if (input == "add")
    {
        Serial.println("Adding 1 to the value");
        value++;
    }
    else if (input == "sub")
    {
        Serial.println("Subtracting 1 from the value");
        value--;
    }
    else
    {
        Serial.println("Unknown command: " + input);
    }

    //Echo the changes
    Serial.print("The current value is: ");
    Serial.println(value);
}

/**********************************/
/* Default methodes               */
/**********************************/

void setup() {

    SoftSerial.begin(SERIAL_BAUD);
    Serial.begin(SERIAL_BAUD);
    
    #ifdef PIN_PWR_RN2483
        pinMode(PIN_PWR_RN2483, OUTPUT);
        digitalWrite(PIN_PWR_RN2483, HIGH);
    #endif
    
    //Serial.begin(SERIAL_BAUD);
    //debugSerial.begin(SERIAL_BAUD);
    MODEM_SERIAL.begin(modem.getDefaultBaudRate());
    
    pinMode(2, INPUT_PULLUP);

    //Serial.begin(SERIAL_BAUD);
    //gpsSerial.begin(SERIAL_BAUD);
}

void loop() {

    if (connection) {

        if (!canSendFromQueue) {
            canSendFromQueue = libTest.performChecks(); 
            updateQueueStatus();
        }
        else {
            libTest.processQueue();
            canSendFromQueue = false;
            updateQueueStatus();
            return;
        }

        if (sendGPS) {
            sendGPS = false;

            sendGPSData();
            sendSensor();

            ++sensorSelect %= 17;

            updateQueueStatus();
        }
        else {
            if (++delayCnt >= 2) {
                delayCnt = 0;
                sendGPS = true;
            }
        }
    }

    delay(500);
}

/**********************************/
/* Andere methodes                */
/**********************************/

void wakeUP_RN2483() {
    MODEM_SERIAL.end();
    pinMode(PIN_TX_RN2483, OUTPUT);
    digitalWrite(PIN_TX_RN2483, LOW);
    delay(5);
    digitalWrite(PIN_TX_RN2483, HIGH);
    MODEM_SERIAL.begin(modem.getDefaultBaudRate());
    MODEM_SERIAL.write(0x55);
}

void attemptModemConnection() {
    byte cntWakeUp = 0;
    
    while (!connection) {
        wakeUP_RN2483(); // RN2483 specific -> see if we can move it into modem class itself
        cntWakeUp++;
        
        if (libTest.connect(DEV_ADDR, APPSKEY, NWKSKEY, true)) {
            connection = true;
            modem.storeTimeOnAirBudget(30000);
            pinMode(PIN_Q_FULL, OUTPUT);
            digitalWrite(PIN_Q_FULL, LOW);
            pinMode(PIN_Q_MT, OUTPUT);
            digitalWrite(PIN_Q_MT, HIGH);
            pinMode(PIN_Q_IN_BETWEEN, OUTPUT);
            digitalWrite(PIN_Q_IN_BETWEEN, LOW);
            break;
        }
        else {
            #ifdef PIN_PWR_RN2483
                if (cntWakeUp > 3) {
        
                    digitalWrite(PIN_PWR_RN2483, LOW);
                    delay(2500);
                    digitalWrite(PIN_PWR_RN2483, HIGH);
                    delay(2500);
                }
                else {
            #endif
                    delay(5000);
            #ifdef PIN_PWR_RN2483
                }
            #endif
        }
    }
}

int freeRam() {
    extern int __heap_start, *__brkval;
    int v;
    return (int) &v - (__brkval == 0 ? (int) &__heap_start : (int) __brkval);
}

void updateQueueStatus() {
    return;

    if (libTest.sendQueueIsEmpty()) {
        digitalWrite(PIN_Q_MT, HIGH);
        digitalWrite(PIN_Q_IN_BETWEEN, LOW);
        digitalWrite(PIN_Q_FULL, LOW);
    }
    else if (libTest.sendQueueIsFull()) {
        digitalWrite(PIN_Q_FULL, HIGH);
        digitalWrite(PIN_Q_MT, LOW);
        digitalWrite(PIN_Q_IN_BETWEEN, LOW);
    }
    else {
        digitalWrite(PIN_Q_IN_BETWEEN, HIGH);
        digitalWrite(PIN_Q_MT, LOW);
        digitalWrite(PIN_Q_FULL, LOW);
    }
}

void dumpSendResult(Sensor& sns){
    bool sendResult = libTest.send(sns, true);
}

void wakeUp() {
    detachInterrupt(IRQ);
    sendGPS = true;
}

void addMillis(unsigned long inc_millis) {
    uint8_t oldSREG = SREG;
    cli();
    timer0_millis += inc_millis;
    SREG = oldSREG;
}
