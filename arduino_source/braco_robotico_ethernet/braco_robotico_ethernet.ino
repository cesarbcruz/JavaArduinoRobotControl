#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress serverIP(192,168,0,100);

int serverPort = 8888;
int led_port = 13;

String readString;

EthernetServer server(serverPort);

void setup()
{
  Serial.begin(9600);
  Ethernet.begin(mac, serverIP);
  server.begin();
  Serial.print("Server online.");

  pinMode(led_port, OUTPUT);
}

void loop()
{
  EthernetClient client = server.available();
  if (client) {

  while (client.connected()) {
        if (client.available()) {
       
          char c = client.read();
       
          if (readString.length() < 30) {
           readString.concat(c);
          }
       
          Serial.print("Command: ");
          Serial.println(readString);
       
          if (readString == "led1") {
           digitalWrite(led_port, HIGH);
           Serial.println("Led status: 1 (ON)");
           resetString();
          }
       
          if (readString == "led0") {
           digitalWrite(led_port, LOW);
           Serial.println("Led status: 0 (OFF)");
           resetString();
          }
       
   }
  }
        delay(1);
  client.stop();
  }
  }

  void resetString() {
   readString = "";
  }
