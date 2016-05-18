#include <SPI.h>
#include <Ethernet.h>
 
// Entre com os dados do MAC e ip para o dispositivo.
// Lembre-se que o ip depende de sua rede local
byte mac[] = { 
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(192,168,0,100);
 
// Inicializando a biblioteca Ehternet
// 80 é a porta que será usada. (padrão http)
EthernetServer server(80);
 
void setup() {
 // Abrindo a comunicação serial para monitoramento.
  Serial.begin(9600);
   while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }
 
  // Inicia a conexão Ethernet e o servidor:
  Ethernet.begin(mac, ip);
  server.begin();
  Serial.print("Servidor iniciado em: ");
  Serial.println(Ethernet.localIP());
}
 
void loop() {
  // Aguardando novos clientes;
  EthernetClient client = server.available();
  if (client) {
    Serial.println("Novo Cliente");
    // Uma solicitação http termina com uma linha em branco
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
        // Se tiver chegado ao fim da linha (recebeu um novo 
        // Caractere) e a linha estiver em branco, o pedido http terminou,
        // Para que você possa enviar uma resposta
        if (c == '\n' && currentLineIsBlank) {
          // Envia um cabeçalho de resposta HTTP padrão
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // a conexão será fechada após a conclusão da resposta
      client.println("Refresh: 5");  // atualizar a página automaticamente a cada 5 segundos
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          // Saída do valor de cada pino de entrada analógica (A0 à A5)
          for (int analogChannel = 0; analogChannel < 6; analogChannel++) {
            int sensorReading = analogRead(analogChannel);
            client.print("Entrada analógica ");
            client.print(analogChannel);
            client.print(" , valor: ");
            client.print(sensorReading);
            client.println("<br />");       
          }
          client.println("</html>");
          break;
        }
        if (c == '\n') {
          // Você está começando uma nova linha
          currentLineIsBlank = true;
        } 
        else if (c != '\r') {
          // Você recebeu um caracter na linha atual.
          currentLineIsBlank = false;
        }
      }
    }
    // Dar tempo ao navegador para receber os dados
    delay(1);
    // Fecha a conexão:
    client.stop();
    Serial.println("Cliente desconectado");
  }
}
