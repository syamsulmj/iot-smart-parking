#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(13,15); // RX(D7)13, TX(D8)15 nodemcu 

#define USE_SERIAL Serial

ESP8266WiFiMulti WiFiMulti;

HTTPClient http;

String state[4];
String id;
String receivedData;

int httpCode;

void setup() {
    USE_SERIAL.begin(9600);
    mySerial.begin(9600);

    connectWifi();
}

void loop() {
  RECEIVED:
  if((WiFiMulti.run() == WL_CONNECTED)) {
    
    // received data
    http.begin("http://ipscapstone.tech/arduino_received_parking_status.php");

    httpCode = http.GET();
    if(httpCode > 0) {
        USE_SERIAL.printf("[HTTP] GET... code: %d\n", httpCode);

        // file found at server
        if(httpCode == HTTP_CODE_OK) {
            String payload = http.getString();
            USE_SERIAL.println(payload);
            receivedData = payload;
        }
    } 
    else {
        USE_SERIAL.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
    }

    mySerial.println(String(receivedData[0]) + "," + String(receivedData[2]) + "," + String(receivedData[4]) + "," + String(receivedData[6]));
    receivedData = "";
  }
  else {
    delay(1000);
    goto RECEIVED;
  }
  
  delay(300);

  if(mySerial.available()>0){
        while(mySerial.available()){
          id += (char) mySerial.read();//receive data
        }
        //USE_SERIAL.println(id);
        //USE_SERIAL.println(id.length());
        //USE_SERIAL.println(id[0]);
        state[0] = id[0];
        state[1] = id[2];
        state[2] = id[4];
        state[3] = id[6];

//        for(int i=0; i<id.length(); i++) {
//          if(id[i] != ',' || id[i] != NULL) {
//            state[i] = id[i];
//          }
//        }

        for(int i=0; i<4; i++) {
          USE_SERIAL.println(state[i]);  
        }

//        delay(1000);

        AGAIN:
        if((WiFiMulti.run() == WL_CONNECTED)) {

          http.begin("http://ipscapstone.tech/arduino_change_parking_status.php?parking1=" + state[0] + "&parking2=" + state[1] + "&parking3=" + state[2] + "&parking4=" + state[3]);

  
          httpCode = http.GET();
          if(httpCode > 0) {
              USE_SERIAL.printf("[HTTP] GET... code: %d\n", httpCode);
  
              // file found at server
              if(httpCode == HTTP_CODE_OK) {
                  String payload = http.getString();
                  USE_SERIAL.println(payload);
                  //ledstatus = payload;
              }
          } 
          else {
              USE_SERIAL.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
          }
  
          http.end();

          //Parking No 1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
          

          http.begin("http://ipscapstone.tech/arduino_update_parking.php?grid=1-1");

          httpCode = http.GET();
          if(httpCode > 0) {
              USE_SERIAL.printf("[HTTP] GET... code: %d\n", httpCode);
  
              // file found at server
              if(httpCode == HTTP_CODE_OK) {
                  String payload = http.getString();
                  USE_SERIAL.println(payload);
                  //ledstatus = payload;
              }
          } 
          else {
              USE_SERIAL.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
          }
  
          http.end();

          //Parking No 2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
          
          http.begin("http://ipscapstone.tech/arduino_update_parking.php?grid=1-2");

          httpCode = http.GET();
          if(httpCode > 0) {
              USE_SERIAL.printf("[HTTP] GET... code: %d\n", httpCode);
  
              // file found at server
              if(httpCode == HTTP_CODE_OK) {
                  String payload = http.getString();
                  USE_SERIAL.println(payload);
                  //ledstatus = payload;
              }
          } 
          else {
              USE_SERIAL.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
          }
  
          http.end();

          //Parking No 3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

          http.begin("http://ipscapstone.tech/arduino_update_parking.php?grid=2-2");

          httpCode = http.GET();
          if(httpCode > 0) {
              USE_SERIAL.printf("[HTTP] GET... code: %d\n", httpCode);
  
              // file found at server
              if(httpCode == HTTP_CODE_OK) {
                  String payload = http.getString();
                  USE_SERIAL.println(payload);
                  //ledstatus = payload;
              }
          } 
          else {
              USE_SERIAL.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
          }
  
          http.end();

          //Parking No 4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

          http.begin("http://ipscapstone.tech/arduino_update_parking.php?grid=2-2");

          httpCode = http.GET();
          if(httpCode > 0) {
              USE_SERIAL.printf("[HTTP] GET... code: %d\n", httpCode);
  
              // file found at server
              if(httpCode == HTTP_CODE_OK) {
                  String payload = http.getString();
                  USE_SERIAL.println(payload);
                  //ledstatus = payload;
              }
          } 
          else {
              USE_SERIAL.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
          }
  
          http.end();
         
        }
        else {
          //connectWifi();
          delay(1000);
          goto AGAIN;
        }

      //mySerial.println(receivedData[0]+","+receivedData[2]+","+receivedData[4]+","+receivedData[6]);
      //receivedData = "";
      delay(1000);
    }

}

void connectWifi(){
  USE_SERIAL.setDebugOutput(true);

    USE_SERIAL.println();
    USE_SERIAL.println();
    USE_SERIAL.println();

    for(uint8_t t = 4; t > 0; t--) {
        USE_SERIAL.printf("[SETUP] WAIT %d...\n", t);
        USE_SERIAL.flush();
        delay(1000);
    }

    WiFiMulti.addAP("OnePlus 3T", "satuduatiga");

    // allow reuse (if server supports it)
    http.setReuse(true);
}


