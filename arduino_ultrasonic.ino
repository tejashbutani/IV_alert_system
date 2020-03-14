int lengthMeasured;
long timeDuration; 

int inputPin = 7;
int outputPin = 8;



void setup(){
  Serial.begin(9600);
  pinMode(inputPin,INPUT);
  pinMode(outputPin,OUTPUT);
  
  Serial.println("Module Started!!");
 }

void loop(){
  digitalWrite(outputPin,LOW);
  delay(20);
  digitalWrite(outputPin,HIGH);
   delay(100);
  digitalWrite(outputPin,LOW);

  timeDuration = pulseIn(inputPin, HIGH);
 lengthMeasured = timeDuration*(3.4/2);
  //lengthMeasured = (timeDuration/2)/29.1;
  Serial.print("Length : ");
  Serial.println(lengthMeasured);
  delay(100);
  }
