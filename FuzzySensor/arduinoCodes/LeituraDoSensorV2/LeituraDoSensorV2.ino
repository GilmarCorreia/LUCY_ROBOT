#include "TouchSensorSerial.h"

TouchSensorSerial TS8;

void setup() {
  Serial.begin(9600);
}

void loop() {
  TS8.runSensor();
}
