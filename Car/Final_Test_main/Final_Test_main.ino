#include <boarddefs.h>
#include <IRremote.h>
#include <IRremoteInt.h>
#include <ir_Lego_PF_BitStreamEncoder.h>
#include <Servo.h>
#include <LiquidCrystal.h>


      boolean order = false;   //Working signal.
      int abc = 10;           //Signal of working to No.2 CPU.
      
      char signal1;  //The signal that make avoidance and MP3 work.
      char signal2;  //The signal that make MP3 play specific record.
      char signal3;  //Receive signal, and change the direction of the dog.
      char signal4;  //Break the loop.
      //Setting the pins of the power amplifier moudle 
      //LED
       const int ledpin  = 22;  //Set the pin of the lamp.
      //The lCD panel
       LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
      
      //The setting of sensors
      //photoswitch
      int potpin  = A0;   //The pin to analyse the intensity of light
      int val = 0;        //To analyse the intensity of light
      
      //Shake sensor
      int vibration = 18; //The pin of the shake sensor

      //IR evading obstacle sensor
      int infrared = 19;  //The pin of the IR evading obstacle sensor
      
      //IR control
      int RECV_PIN = 8;
      IRrecv irrecv(RECV_PIN);
      decode_results results;

      //Bluetooth
      int blueSingal = 0;

      //The initial location of Servo
      Servo myservo;
      int pos = 90;

      //Stepping motor
      //Right wheel
        int wheel1_pin1 = 24;
        int wheel1_pin2 = 25;
        int wheel1_pin3 = 26;
        int wheel1_pin4 = 27;
      //Left wheel 
        int wheel2_pin1 = 28;
        int wheel2_pin2 = 29;
        int wheel2_pin3 = 30;
        int wheel2_pin4 = 31;
       //The initial state of the motors
       //Go
       int _step1 = 0;
       int _step2 = 0;
       //Back
       int _step3 = 0;
       int _step4 = 0;
//**************************************************************************
      //Light
      void Light(){
        val = analogRead(potpin);
        if(val<200){
        digitalWrite(ledpin, HIGH);
        }
        else{
        digitalWrite(ledpin, LOW);
        }
        }
      //Height
      void hight(){
            Serial3.print('q');
        }
      //Shake
      void Vibration(){  
            Serial3.print('p');
        }
      //Control of Direction
      void Receive_direction(){
        go();
        if(Serial3.available()>0){
        signal3 = Serial3.read();
        while(signal3 == 's'){
        off();
        signal4 = Serial3.read();
          
        if(signal4 == 'l'){
          left();
          break;
        }
        else if(signal4 == 'r'){
          right();
          break;
        }
        else if(signal4 == 'g'){
          break;
          }
      } 
     } 
   }
     //IR Control
     void Control(){
      if(irrecv.decode(&results)){
        Serial.println(results.value, HEX);
        irrecv.resume();
        
          if(results.value==0xFFA25D||results.value==0xE318261B){
            order = true;//Work
            }
          else if(results.value==0x511DBB||results.value==0xFF629D){
            order = false;//Stop work
            }
          else if(results.value==0xFFA857||results.value==0xA3C8EDDB){
            go();
            }
          else if(results.value==0xFF22DD||results.value==0x52A3D41F){
            left();
            }
          else if(results.value==0xFF02FD||results.value==0xD7E84B1B){
            right();
            }
          else if(results.value==0xFFE01F||results.value==0xF076C13B){
            back();
            }
        }
      }
      //Bluetooth Control
      void bluetooth(){
      while(Serial2.available()){
            blueSingal = Serial2.read();
            if(blueSingal==0){
               back();
            }
            else if(blueSingal==1){
              //go();
              order = true;
            }
            else if(blueSingal==2){
              left();
            }
            else if(blueSingal==3){
              right();
            }
            else if(blueSingal==4){
              order = false;
            }
          }
    }
      //Go
      void go(){
        for(int i=0;i<140;i++){
          up1();
          up2();
        }
      }
      //Back
      void back(){
        for(int i=0;i<200;i++){
          down1();
          down2();
        }
      }
      //Turn right
      void right(){
      for(int i=0;i<300;i++){
          up2();
          }
      }

      //Turn left
      void left(){
      for(int i=0;i<300;i++){
          up1();
          }
      }
      //Stop
      void off(){
      digitalWrite(wheel1_pin1, LOW);
      digitalWrite(wheel1_pin2, LOW);
      digitalWrite(wheel1_pin3, LOW);
      digitalWrite(wheel1_pin4, LOW);

      digitalWrite(wheel2_pin1, LOW);
      digitalWrite(wheel2_pin2, LOW);
      digitalWrite(wheel2_pin3, LOW);
      digitalWrite(wheel2_pin4, LOW);
      }
      //Right wheel forward
       void up1()
       {  
        switch(_step1){
        case 0:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, HIGH);
        break;
        case 1:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, HIGH);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, HIGH);
        break;
        case 2:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, HIGH);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 3:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, HIGH);
          digitalWrite(wheel1_pin3, HIGH);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 4:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, HIGH);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 5:
          digitalWrite(wheel1_pin1, HIGH);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, HIGH);
          digitalWrite(wheel1_pin4, LOW);
        break;
          case 6:
          digitalWrite(wheel1_pin1, HIGH);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 7:
          digitalWrite(wheel1_pin1, HIGH);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, HIGH);
        break;
        default:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, LOW);
        break;
      }
       _step1++;
     
      if(_step1>7){    
        _step1=0;  
        }
      delay(2);
      }
      //Left wheel forward
      void up2(){
       switch(_step2){
      case 0:
        digitalWrite(wheel2_pin1, HIGH);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, HIGH);
      break;
      case 1:
        digitalWrite(wheel2_pin1, HIGH);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 2:
        digitalWrite(wheel2_pin1, HIGH);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, HIGH);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 3:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, HIGH);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 4:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, HIGH);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 5:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, LOW);
      break;
        case 6:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, HIGH);
      break;
      case 7:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, HIGH);
      break;
      default:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, LOW);
      break;
    }
     _step2++;
   
    if(_step2>7){    
      _step2=0;  
      }
      delay(2);
      }
      //Right wheel back
      void down1(){
      switch(_step3){
        case 0:
          digitalWrite(wheel1_pin1, HIGH);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, HIGH);
        break;
        case 1:
          digitalWrite(wheel1_pin1, HIGH);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 2:
          digitalWrite(wheel1_pin1, HIGH);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, HIGH);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 3:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, HIGH);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 4:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, HIGH);
          digitalWrite(wheel1_pin3, HIGH);
          digitalWrite(wheel1_pin4, LOW);
        break;
        case 5:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, HIGH);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, LOW);
        break;
          case 6:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, HIGH);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, HIGH);
        break;
        case 7:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, HIGH);
        break;
        default:
          digitalWrite(wheel1_pin1, LOW);
          digitalWrite(wheel1_pin2, LOW);
          digitalWrite(wheel1_pin3, LOW);
          digitalWrite(wheel1_pin4, LOW);
        break;
      }
       _step3++;
     
      if(_step3>7){    
        _step3=0;  
        }
      delay(2);
      }
      //Left wheel back
      void down2()
      {  
      switch(_step4){
      case 0:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, HIGH);
        
      break;
      case 1:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, HIGH);
        
        
      break;
      case 2:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 3:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, HIGH);
        digitalWrite(wheel2_pin4, LOW);

      break;
      case 4:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, HIGH);
        digitalWrite(wheel2_pin3, HIGH);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 5:
        digitalWrite(wheel2_pin1, HIGH);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, HIGH);
        digitalWrite(wheel2_pin4, LOW);
      break;
        case 6:
        digitalWrite(wheel2_pin1, HIGH);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, LOW);
      break;
      case 7:
        digitalWrite(wheel2_pin1, HIGH);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, HIGH);
      break;
      default:
        digitalWrite(wheel2_pin1, LOW);
        digitalWrite(wheel2_pin2, LOW);
        digitalWrite(wheel2_pin3, LOW);
        digitalWrite(wheel2_pin4, LOW);
      break;
    }
     _step4++;
   
    if(_step4>7){    
      _step4=0;  
    }
      delay(2);
      }
//**************************************************************************************************
void setup() {
  
  //The initialization of  power amplifier module 
  //LED
    pinMode(ledpin,OUTPUT);
  //LCD
        lcd.begin(16, 2);
        lcd.print("Hello!");
        lcd.setCursor(0, 1);
        lcd.print("I am a Guide Dog");
    
  //The initialization of sensors
  //IR avoidance
    pinMode(infrared,INPUT);
       
  //IR Control
    irrecv.enableIRIn();
    
  //The pin of servo
    myservo.attach(7);  
    
  //External interruption（shake and IR）
    attachInterrupt(4, hight, FALLING);
    //attachInterrupt(5, Vibration, FALLING);
    
  //The initialization of motor
        pinMode(wheel1_pin1,OUTPUT);
        pinMode(wheel1_pin2,OUTPUT);
        pinMode(wheel1_pin3,OUTPUT);
        pinMode(wheel1_pin4,OUTPUT);
        
        pinMode(wheel2_pin1,OUTPUT);
        pinMode(wheel2_pin2,OUTPUT);
        pinMode(wheel2_pin3,OUTPUT);
        pinMode(wheel2_pin4,OUTPUT);
        
   //Setting the pin of motor as low level
        digitalWrite(wheel1_pin1,LOW);
        digitalWrite(wheel1_pin2,LOW);
        digitalWrite(wheel1_pin3,LOW);
        digitalWrite(wheel1_pin4,LOW);
        
        digitalWrite(wheel2_pin1,LOW);
        digitalWrite(wheel2_pin2,LOW);
        digitalWrite(wheel2_pin3,LOW);
        digitalWrite(wheel2_pin4,LOW);
   //Setting LED as off
     digitalWrite(ledpin,LOW);
        
  //The initialization of serial monitor
    Serial.begin(9600);
    Serial2.begin(9600);
    Serial3.begin(9600);
}

void loop() {
    
  //Work
  if(order){
    //NO.2 CPU Work
    if(abc>0){
      Serial3.print('a');
      abc = 0;
      }
    Control();
    bluetooth();
    Light();
    Receive_direction();
    }
    
  //Stop Work 
  else{
    Serial3.print('b');
    abc = 10;
    off();
    Control();
    bluetooth();
    digitalWrite(ledpin,LOW);
    }
}
