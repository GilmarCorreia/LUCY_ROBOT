#include "Arduino.h"
#include "TouchSensorSerial.h"

uint16_t TouchSensorSerial::force;
volatile unsigned long TouchSensorSerial::initialTime, TouchSensorSerial::finalTime, TouchSensorSerial::touchTime =0;
int TouchSensorSerial::sendSerial = 0;
  
/*************************************** CONSTRUCTORS ***************************************/


TouchSensorSerial::TouchSensorSerial(uint8_t IN_neg_port,uint8_t IN_pos_port){
  setIN_ports(IN_neg_port,IN_pos_port);
}

/*************************************** SETTERS ***************************************/

void TouchSensorSerial::setIN_ports(uint8_t IN_neg_port,uint8_t IN_pos_port){
  pinMode(IN_neg_port, INPUT);
  attachInterrupt(digitalPinToInterrupt(IN_neg_port), TouchSensorSerial::pulsoNegativo, RISING);
  pinMode(IN_pos_port, INPUT);
  attachInterrupt(digitalPinToInterrupt(IN_pos_port), TouchSensorSerial::pulsoPositivo, RISING);
}

/*************************************** INTERRUPTIONS ***************************************/
static void TouchSensorSerial::pulsoPositivo(){
  initialTime = millis();
  sendSerial = TS_FORCE_BEGIN;
}

static void TouchSensorSerial::pulsoNegativo(){
  finalTime = millis();
  sendSerial = TS_FORCE_END;
}

/*************************************** METHODS ***************************************/

void TouchSensorSerial::serial(int type,int ID){
  if (type == TS_FORCE_BEGIN){
    this->force = analogRead(peakPort);
    
    uint16_t *f=new uint16_t[2]{(this->force&0xff),(this->force>>8)};
    int checkSum = (~(TS_WRITE+TS_FORCE_BEGIN_LENGTH+TS_FORCE_BEGIN+ f[0]+f[1]))&0xFF;
    
    Serial.print(TS_START);
    Serial.print(",");
    Serial.print(TS_START);
    Serial.print(",");
    Serial.print(TS_WRITE);
    Serial.print(",");
    Serial.print(TS_FORCE_BEGIN_LENGTH);
    Serial.print(",");
    Serial.print(TS_FORCE_BEGIN);
    Serial.print(",");
    Serial.print(f[0]);
    Serial.print(",");
    Serial.print(f[1]);
    Serial.print(",");
    Serial.println(checkSum);
    delayMicroseconds(TX_DELAY_TIME);
  }
  else if (type == TS_FORCE_END){
    this->force = analogRead(peakPort);
   
    //unsigned long *t = new unsigned long[2]{((finalTime-initialTime)&0xff),((finalTime-initialTime)>>8)};
    int checkSum = (~(TS_WRITE+TS_FORCE_END_LENGTH+TS_FORCE_END+ 255))&0xFF;
    
    Serial.print(TS_START);
    Serial.print(",");
    Serial.print(TS_START);
    Serial.print(",");
    Serial.print(TS_WRITE);
    Serial.print(",");
    Serial.print(TS_FORCE_END_LENGTH);
    Serial.print(",");
    Serial.print(TS_FORCE_END);
    Serial.print(",");
    Serial.print(255);
    Serial.print(",");
    Serial.println(checkSum);
    delayMicroseconds(TX_DELAY_TIME);
    sendSerial = 0;
  }
  else if (type == TS_PIEZO){
    int piezoValue;
    if(ID == 'A')
      piezoValue = analogRead(piezoPort);

    uint16_t *pv = new uint16_t[2]{piezoValue&0xff,piezoValue>>8};
    int checkSum = (~(TS_WRITE+TS_PIEZOWRITE_LENGTH+TS_PIEZO+pv[0]+pv[1]))&0xFF;
    
    Serial.print(TS_START);  
    Serial.print(",");
    Serial.print(TS_START);
    Serial.print(",");
    Serial.print(TS_WRITE);
    Serial.print(",");
    Serial.print(TS_PIEZOWRITE_LENGTH);
    Serial.print(",");
    Serial.print(TS_PIEZO);
    Serial.print(",");
    Serial.print(pv[0]);
    Serial.print(",");
    Serial.print(pv[1]);
    Serial.print(",");
    Serial.println(checkSum);
    delayMicroseconds(TX_DELAY_TIME);
    sendSerial = 0;
  }
  else if (type == TS_POT){
    int potValue;
    if(ID == 'A')
      potValue = analogRead(pot_neg);
    if(ID == 'B')
      potValue = analogRead(pot_pos);

    uint16_t *pv = new uint16_t[2]{(potValue&0xff),(potValue>>8)};
    int checkSum = (~(TS_WRITE+TS_PIEZOWRITE_LENGTH+TS_PIEZO+pv[0]+pv[1]))&0xFF;
    
    Serial.print(TS_START);  
    Serial.print(",");
    Serial.print(TS_START);
    Serial.print(",");
    Serial.print(TS_WRITE);
    Serial.print(",");
    Serial.print(TS_POT_LENGTH);
    Serial.print(",");
    Serial.print(TS_PIEZO);
    Serial.print(",");
    Serial.print(pv[0]);
    Serial.print(",");
    Serial.print(pv[1]);
    Serial.print(",");
    Serial.println(checkSum);
    delayMicroseconds(TX_DELAY_TIME);
    sendSerial = 0;
  }
}

void TouchSensorSerial::verifyBuffer(int* buf){
  if(this->buf[1]== TS_START){
    if(this->buf[2] == TS_READ){
      uint8_t lengthMsg = this->buf[3];
      uint8_t type = this->buf[4];
      int checkSum;
      switch (type){
        case TS_PIEZO:
          int piezoID = 0;
          piezoID = this->buf[5];
          checkSum = (~(TS_READ+TS_PIEZO_LENGTH+TS_PIEZO+piezoID))&0xFF;
          if (checkSum == this->buf[6])
            serial(TS_PIEZO,piezoID);
          else
            Serial.println("Message Error");
          break;
          
        case TS_POT:
          int potID = 0;
          potID = this->buf[5];
          checkSum = (~(TS_READ+TS_POT_LENGTH+TS_POT+potID))&0xFF;
          if (checkSum == this->buf[6])
            serial(TS_PIEZO,potID);
          else
            Serial.println("Message Error");
          break;
      }
    }
  }
  else
    Serial.println("Error");
}

void TouchSensorSerial::clearBuffer(){
  for (uint8_t  index= 0; index<(sizeof(buf)/sizeof(int)); index++)
    buf[index] = 0;
}

int TouchSensorSerial::convert(int binary){
  if (binary == 1)
    return 256;
  else if(binary == 2)
    return 512;
  else if (binary == 3)
    return 768;
  else
    return 0;
}

void TouchSensorSerial::runSensor(){
  while (Serial.available()>0){
    this->buf[bufIndex] = Serial.read();
    /*Serial.print(bufIndex);
    Serial.print(": ");
    Serial.println(buf[bufIndex]);*/
    this->bufIndex++;
    delay(RX_DELAY_TIME);
  }

  serial(this->sendSerial,0);  

  if(this->buf[0] == TS_START && Serial.available()<=0)
    verifyBuffer(buf);
  
  bufIndex = 0;
  clearBuffer();
}

/*
void TouchSensorSerial::runSensor(){
  while (Serial.available()>0){
    this->buf[bufIndex] = Serial.read();
    Serial.print(bufIndex);
    Serial.print(": ");
    Serial.println(buf[bufIndex]);
    this->bufIndex++;
    delay(RX_DELAY_TIME);
  }
  
  serial(this->sendSerial,0);
    
  while(this->control){
    this->force = analogRead(peakPort);
    if (this->maxForce < this->force)
      this->maxForce = this->force; 
  }

  if(this->buf[0] == TS_START && Serial.available()<=0)
    verifyBuffer(buf);
  
  bufIndex = 0;
  clearBuffer();
}*/
 
