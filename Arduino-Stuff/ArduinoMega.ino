#include<NewPing.h>
#include <LiquidCrystal.h>
//#include <SoftwareSerial.h>

const int rs = 22, en = 23 , d4 = 24, d5 = 25, d6 = 26, d7 = 27;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

//NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
NewPing sonar[4]={
  NewPing (2,6,200),
  NewPing (3,7,200),
  NewPing (4,8,200),
  NewPing (5,9,200),
  };
//         1-1 1-2 2-1 2-2
int ledr[4]={10,51,52,53};
int ledg[4]={11,47,48,49};
int ledb[4]={12,43,44,45};
int ledy[4]={13,39,40,41};

String state[4];
String checkState[4];

int lcdDisplay[4];

int statelcd[4];

int empty=0;
int able=0;

void setup() {
  Serial.begin(9600);
  Serial2.begin(9600);
  lcd.begin(16,2); // Initializes the interface to the LCD screen, and specifies the dimensions (width and height) of the display
//  pinMode (ledr[4],OUTPUT); 
//  pinMode (ledg[4],OUTPUT);
//  pinMode (ledb[4],OUTPUT);
//  pinMode (ledy[4],OUTPUT);

  for(int i=0; i<4; i++) {
    pinMode (ledr[i],OUTPUT); 
    pinMode (ledg[i],OUTPUT);
    pinMode (ledb[i],OUTPUT);
    pinMode (ledy[i],OUTPUT);
  }
  
  // put your setup code here, to run once:

}

void loop() {

  receivedData();
  delay(300);
  
  for(int i=0; i<4; i++){
    
    state[i] = checkSensor(sonar[i]);

    if(checkState[i] == "2" || checkState[i] == "3") {
      state[i] = 1;
    }
    
  }
 
  sendingData(state[0],state[1],state[2],state[3]);
  sendingLED(state[0],state[1],state[2],state[3]);
  lcdplay(state[0],state[1],state[2],state[3]);

  //  // put your main code here, to run repeatedly:
  delay(1000);
}

String checkSensor(NewPing sonar){
  if(sonar.ping_cm()<10){
    return "1";
   
  }
  else{
    return "0";
    
  }
}

void sendingData(String state1, String state2, String state3, String state4){
  Serial2.println(state1 + "," + state2 + "," + state3 + "," + state4);

}

void receivedData() {
  String receive = "";
  if(Serial2.available()>0) {
    while(Serial2.available()){
          receive += (char) Serial2.read();//receive data
    }

    checkState[0] = receive[0];
    checkState[1] = receive[2];
    checkState[2] = receive[4];
    checkState[3] = receive[6];
  }
}

void sendingLED(String state1, String state2, String state3, String state4)
{
  String ledstate[4]={state1,state2,state3,state4};

  for(int i; i<4; i++){
    if(ledstate[i]=="1" && checkState[i] != "2" && checkState[i] != "3"){
      digitalWrite (ledr[i],HIGH);
      digitalWrite (ledg[i],LOW);
      digitalWrite (ledb[i],LOW);
      digitalWrite (ledy[i],LOW);
      
    }
    else if(ledstate[i]=="1" && checkState[i] == "2") {
      digitalWrite (ledr[i],LOW);
      digitalWrite (ledg[i],LOW);
      digitalWrite (ledb[i],LOW);
      digitalWrite (ledy[i],HIGH);
    }
    else if(ledstate[i]=="1" && checkState[i] == "3") {
      digitalWrite (ledr[i],LOW);
      digitalWrite (ledg[i],LOW);
      digitalWrite (ledb[i],HIGH);
      digitalWrite (ledy[i],LOW);
    }
    else{
      digitalWrite (ledr[i],LOW);
      digitalWrite (ledb[i],LOW);
      digitalWrite (ledy[i],LOW);
      digitalWrite (ledg[i],HIGH);
    }
  }
  
}



void lcdplay(String state1, String state2, String state3, String state4) {

  String lcdstate[4]={state1, state2, state3, state4};

  for (int i; i<4; i++)
  {
    if (lcdstate[i]=="1")
    {
      lcdDisplay[i] = 1;
    }
    else{
      lcdDisplay[i] = 0;
    }
  }

  delay(100);
  check(lcdDisplay[0], lcdDisplay[1], lcdDisplay[2],lcdDisplay[3]); 
  
}

void check(int statelcd1, int statelcd2, int statelcd3,int statelcd4){
  int sum;

  sum = statelcd1 + statelcd2 + statelcd3 + statelcd4;

  empty = 4 - sum;
  able = sum;
  displaylcd();
  
}


void displaylcd(){
lcd.setCursor(0,0); //text to print
lcd.print("AVAILABLE:"); //9 
lcd.setCursor(11,0); //text to print
lcd.print(empty); 
lcd.setCursor(0,1); //text to print
lcd.print("OCCUPIED:"); //8
lcd.setCursor(10,1); //text to print
lcd.print(able);

}
