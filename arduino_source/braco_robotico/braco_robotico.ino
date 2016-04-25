//Programa : Controle braço robotico


#include <Servo.h>


Servo servo_base;
Servo servo_distancia;
Servo servo_altura;
Servo servo_garra;

String inputString = "";         // a string to hold incoming data




void setup()
{
  Serial.begin(9600);
  servo_garra.attach(22, 1, 180);
  servo_altura.attach(24, 1, 180);
  servo_base.attach(26, 1, 180);
  servo_distancia.attach(28, 1, 180);  
  inputString.reserve(4);
  servo_base.write(90);
  servo_distancia.write(90);
  servo_altura.write(90);
  servo_garra.write(90);
}

void loop()
{
}

void executaComando(String identificadorServo, long valor) {
  if (identificadorServo == "B") {
    servo_base.write(valor);
  }else if (identificadorServo == "G") {
    servo_garra.write(valor);
  }else if (identificadorServo == "A") {
    servo_altura.write(valor);
  }else if (identificadorServo == "D") {
    servo_distancia.write(valor);
  }
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {
      executaComando(inputString.substring(0, 1), (inputString.substring(1,4)).toInt());
      inputString = "";
    }
  }
}
