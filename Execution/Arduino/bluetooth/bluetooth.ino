#include <../SoftwareSerial.h>
// int ledPin = 13;
 
/* This sample code demonstrates the normal use of reading Bluetooth serial port.
It requires the use of SoftwareSerial, and assumes that you have the 9600-baud Bluetooth serial device hooked up on pins 4(rx) and 3(tx).
This way, port 0 and 1 stay clear for uploading and serial monitor.
*/
 
SoftwareSerial ss(20, 21);
 
void setup() {
  // Force the Bluetooth module to communicate 9600 baud on Serial
  ss.begin(9600);
  ss.print("$");
  ss.print("$");
  ss.print("$");
  delay(100);
  ss.println("SU,96");
  delay(100);
  ss.println("---");
  ss.begin(9600);
 
  // 9600 is the default baud rate for the screen module
  Serial.begin( 9600 );
  Serial.print("Start... ");
}
 
void loop() {
  // listen for serial data
  if (ss.available() > 0 ) {
    // read a numbers from serial port
    int count = ss.parseInt();// print out the received number
    if (count > 0) {
      Serial.print("You have input: ");
      Serial.println(String(count));
 
      // blink the LED
      // blinkLED(count);
    }
    // else {
    //   Serial.println("nope");
    // }
  }
}
 
// void blinkLED(int count) {
//   for (int i=0; i < count; i++) {
//     digitalWrite(ledPin, HIGH);
//     delay(500);
//     digitalWrite(ledPin, LOW);
//     delay(500);
//   }
// }