#include <WiFi.h>
#include <PubSubClient.h>
#include <SPI.h>
#include <MFRC522.h>

// ======== CONFIG WIFI ========
const char* ssid = "SEU_SSID";
const char* password = "SUA_SENHA";

// ======== CONFIG MQTT ========
const char* mqtt_server = "192.168.0.100"; // IP do broker (Node-RED ou Mosquitto)
const int mqtt_port = 1883;
const char* mqtt_topic = "acesso/rfid";

WiFiClient espClient;
PubSubClient client(espClient);

// ======== CONFIG RFID ========
#define SS_PIN  21
#define RST_PIN 22
MFRC522 rfid(SS_PIN, RST_PIN);

// ======== LED e BUZZER ========
#define LED_PIN 5
#define BUZZER_PIN 4

void setup() {
  Serial.begin(115200);

  pinMode(LED_PIN, OUTPUT);
  pinMode(BUZZER_PIN, OUTPUT);

  SPI.begin();
  rfid.PCD_Init();

  conectarWiFi();
  client.setServer(mqtt_server, mqtt_port);
}

void loop() {
  if (!client.connected()) {
    reconnectMQTT();
  }
  client.loop();

  if (!rfid.PICC_IsNewCardPresent() || !rfid.PICC_ReadCardSerial()) {
    return;
  }

  // Monta o UID como string
  String uidStr = "";
  for (byte i = 0; i < rfid.uid.size; i++) {
    uidStr += String(rfid.uid.uidByte[i], HEX);
    if (i < rfid.uid.size - 1) uidStr += ":";
  }

  Serial.print("UID detectado: ");
  Serial.println(uidStr);

  // Aciona LED e buzzer
  digitalWrite(LED_PIN, HIGH);
  digitalWrite(BUZZER_PIN, HIGH);
  delay(500);
  digitalWrite(BUZZER_PIN, LOW);
  delay(1000);
  digitalWrite(LED_PIN, LOW);

  // Envia UID via MQTT
  client.publish(mqtt_topic, uidStr.c_str());

  rfid.PICC_HaltA();
  rfid.PCD_StopCrypto1();
}

void conectarWiFi() {
  Serial.print("Conectando ao Wi-Fi: ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Conectado ao Wi-Fi!");
}

void reconnectMQTT() {
  while (!client.connected()) {
    Serial.print("Conectando ao MQTT...");
    if (client.connect("ESP32Client")) {
      Serial.println("Conectado!");
    } else {
      Serial.print("Falha, rc=");
      Serial.print(client.state());
      Serial.println(" Tentando novamente em 5 segundos");
      delay(5000);
    }
  }
}
