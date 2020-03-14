int lengthMeasured;
long timeDuration; 

int inputPin = 5;
int outputPin = 4;

void setup(){
  pinMode(inputPin,INPUT);
  pinMode(outputPin,OUTPUT);
  Serial.begin(115200);
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
  Serial.print("Length : ");
  Serial.println(lengthMeasured);
  delay(10);
  }
