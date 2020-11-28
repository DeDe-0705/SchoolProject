#include <Servo.h>

      char signal1;   //Receive the signal and work
      char signal2;   //Send the signal to NO.1 CPU
      char signal3;   //Receive the signal and play specific cord
      char signal4;   //Receive the signal and play specific cord
      boolean order = false; //The signal of working
      boolean Limit = true; //The time of limit
      
      //The pins of MP3 module
      int play1=2;    
      int play2=3;
      int play3=4;
      int play4=5;
      int play5=6;    
      int play6=8;
      int play7=9;
      int play8=10;
      int play9=11;

      //The pins of Ultrasonic sensor
      const int trig = 13;   //The pin of OUTPUT
      const int echo = 12;   //The pin of INPUT

      //The initial location of servo
      Servo myservo;
      int pos = 90;
      
    void setup() {
    //The initialization of modules
    //Ultrasonic Sensor
    pinMode(trig,OUTPUT);
    pinMode(echo,INPUT);

    //The pin of servo
    myservo.attach(7);

    //MP3 module
    pinMode(play1,OUTPUT);  
    pinMode(play2,OUTPUT);
    pinMode(play3,OUTPUT);
    pinMode(play4,OUTPUT);
    pinMode(play5,OUTPUT);  
    pinMode(play6,OUTPUT);
    pinMode(play7,OUTPUT);
    pinMode(play8,OUTPUT);
    pinMode(play9,OUTPUT);  
    //The initialization of serial monitor
    Serial.begin(9600);
    }
      //Ultrasonic ranging 
          float getDistance(){
          long IntervalTime = 0;
          
          digitalWrite(trig, LOW);
          delayMicroseconds(2);
          digitalWrite(trig, HIGH); 
          delayMicroseconds(10);
          digitalWrite(trig, LOW); 
          
          IntervalTime = pulseIn(echo, HIGH); 
          float distance= IntervalTime/58.00;

          //If the obstacle distance is greater than 1.5m
          if (distance >= 150.0){ 
            IntervalTime = 0;  
            return 150.0;
          }
          //If the obstacle distance is less than 1.5m, return the real distance.
          else{
            IntervalTime = 0;
            return distance;
          }
      }
      //Avoidance
      void avoidance(){
        int pos;        //Angle
        float dis[3];   //Store the distance of obstacles in three directions
        float Now = 0;  //Check again whatever the obstacle is still ahead
    
        dis[1]=getDistance(); 
      
        //Waring in 1m-1.5m
        if(dis[1]>100.0&&dis[1]<150)
        {
            
           digitalWrite(play3,LOW);
           delay(3000);
           stopPlay(); 
        }
        //Warning in 0.6m-1.0m
        if(dis[1]<100.0&&dis[1]>0.0)
        {  
           signal2 = 's';
           Serial.print(signal2);//Stop and dection
           
           digitalWrite(play7,LOW);
           delay(5000);
           stopPlay(); 
          //***************************************  
          for (pos = 90; pos <= 140; pos += 1) 
          { 
            myservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(15);                       // waits 15ms for the servo to reach the position
          }
          dis[2]=getDistance(); 
          
          for (pos = 140; pos >= 40; pos -= 1) 
          {
            myservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(15);                       // waits 15ms for the servo to reach the position
          }
          dis[0]=getDistance();  
           
          for (pos = 40; pos <= 90; pos += 1) 
          { 
            myservo.write(pos);              // tell servo to go to position in variable 'pos'
            delay(15);                       // waits 15ms for the servo to reach the position
          }
          //*************************************
          Now = getDistance();    

          if(Now>0.0&&Now<60){
            
          if(dis[0]<dis[2]) 
          {
            //Turn left
            digitalWrite(play4,LOW);  
            delay(2000);
            stopPlay();
            
            signal2 = 'l';
            Serial.print(signal2);
            
          }
          else  
          {
           //Turn right
           digitalWrite(play5,LOW);
           delay(1500);
           stopPlay();
           
           signal2 = 'r';
           Serial.print(signal2);
           
          } 
        }
        signal2 = 'g';
        Serial.print(signal2);
      }
     }
      //Stop work of MP3
        void stopPlay(){    
        digitalWrite(play1,HIGH);
        digitalWrite(play2,HIGH);
        digitalWrite(play3,HIGH);
        digitalWrite(play4,HIGH);
        digitalWrite(play5,HIGH);
        digitalWrite(play6,HIGH);
        digitalWrite(play7,HIGH);
        digitalWrite(play8,HIGH);
        digitalWrite(play9,HIGH);
        }
        
      //The signal of woring 
        void work_signal(){
          if(Serial.available()>0){
            signal1 = Serial.read();
        if(signal1 == 'a'){
          order = true;
          signal1 =0;
          }
        else if(signal1 == 'b'){
          order = false;
          signal1 =0;
          }
        }
       }
     //Height
     void Hight_warn(){
        if(Serial.available()>0){
            signal3 = Serial.read();
            if(signal3 == 'q'){
              digitalWrite(play6,LOW);
              delay(3000);
              stopPlay();
              
              }
          }
      }
     //The warning of rough of the road 
     void Vibration_warn(){
        if(Serial.available()>0){
          signal4 = Serial.read();
          if(signal4 == 'p'){    
              digitalWrite(play2,LOW);
              delay(3000);
              stopPlay();
              
            }
          }
      }
void loop() {

  //Prologue
  if(Limit){
    stopPlay();
    digitalWrite(play1,LOW);  
    delay(7000);
    stopPlay();
    Limit = false;
    }
    
  if(order){
          work_signal();
          avoidance();
          Hight_warn();
          //Vibration_warn();
    }
  else{
    work_signal();
    }
}
