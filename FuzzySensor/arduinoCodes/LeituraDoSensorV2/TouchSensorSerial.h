#ifndef TouchSensorSerial_h
#define TouchSensorSerial_h

#include "Arduino.h"

// PORTS:
#define peakPort 0
#define piezoPort 1
#define pot_neg 2
#define pot_pos 3

#define IN_pos 2
#define IN_neg 3

//Serial Protocol
#define TS_START 255
#define TS_WRITE 1
#define TS_READ 0
#define TS_FORCE_BEGIN 1
#define TS_FORCE_END 2
#define TS_PIEZO 3
#define TS_POT 4


#define TS_FORCE_BEGIN_LENGTH 4
#define TS_FORCE_END_LENGTH 3
#define TS_PIEZO_LENGTH 3
#define TS_PIEZOWRITE_LENGTH 4
#define TS_POT_LENGTH 3
#define TS_POT_WRITE_LENGTH 4

#define TX_DELAY_TIME 200 //Microseconds
#define RX_DELAY_TIME 10 //Millisseconds

class TouchSensorSerial{
  static uint16_t force;
  static volatile unsigned long initialTime, finalTime, touchTime;
  static int sendSerial;
    
  public:
    TouchSensorSerial():TouchSensorSerial(IN_neg,IN_pos){};
    TouchSensorSerial(uint8_t,uint8_t);
    void runSensor();
  private:
    void setIN_ports(uint8_t,uint8_t);
    static void pulsoPositivo();
    static void pulsoNegativo();
    void serial(int,int);
    void verifyBuffer(int*);
    void clearBuffer();
    int convert(int);
    int buf[20];  
    uint8_t bufIndex = 0;
};

#endif
