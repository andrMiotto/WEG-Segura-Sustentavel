#include <WiFi.h>
#include <PubSubClient.h>
#include <SPI.h>
#include <MFRC522.h>
#include <Wire.h>
#include "RTClib.h"
#include "DHT.h"

// --- DHT ---
#define DHTPIN 25       // Pino do DHT
#define DHTTYPE DHT22   // Tipo do sensor (DHT11 ou DHT22)
DHT dht(DHTPIN, DHTTYPE);

// --- RFID ---
#define SS_PIN   5
#define RST_PIN  22
MFRC522 mfrc522(SS_PIN, RST_PIN);  
// (SCK=18, MOSI=23, MISO=19)

// --- RTC ---
RTC_DS3231 rtc;

// --- LED e buzzer ---
#define LED_PIN     12
#define BUZZER_PIN  26   // Ajustado para D26

// --- WiFi ---
const char* ssid = "SEU_WIFI";
const char* password = "SUA_SENHA";

// --- MQTT (Node-RED) ---
const char* mqtt_server = "192.168.1.100";  // IP do broker (Node-RED/Mosquitto)
WiFiClient espClient;
PubSubClient client(espClient);

// --- VARIÁVEIS ---
int contadorPessoas = 0;

struct Usuario {
  String uid;
  bool dentro;
};
Usuario usuarios[50];
int totalUsuarios = 0;

void setup_wifi() {
  delay(10);
  Serial.println("Conectando ao WiFi...");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWiFi conectado");
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Tentando conexão MQTT...");
    if (client.connect("ESP32_RFID")) {
      Serial.println("Conectado ao broker MQTT");
    } else {
      Serial.print("Falhou, rc=");
      Serial.print(client.state());
      Serial.println(" tentando novamente em 5s...");
      delay(5000);
    }
  }
}

String lerUID(MFRC522::Uid uid) {
  String conteudo = "";
  for (byte i = 0; i < uid.size; i++) {
    conteudo += String(uid.uidByte[i] < 0x10 ? "0" : "");
    conteudo += String(uid.uidByte[i], HEX);
  }
  conteudo.toUpperCase();
  return conteudo;
}

int buscarUsuario(String uid) {
  for (int i = 0; i < totalUsuarios; i++) {
    if (usuarios[i].uid == uid) return i;
  }
  return -1;
}

void setup() {
  Serial.begin(115200);

  pinMode(LED_PIN, OUTPUT);
  pinMode(BUZZER_PIN, OUTPUT);

  // Inicializa SPI e RFID
  SPI.begin(18, 19, 23);  // SCK=18, MISO=19, MOSI=23
  mfrc522.PCD_Init();

  // RTC
  if (!rtc.begin()) {
    Serial.println("Não foi possível encontrar o RTC");
    while (1);
  }
  if (rtc.lostPower()) {
    rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));
  }

  // DHT
  dht.begin();

  // WiFi + MQTT
  setup_wifi();
  client.setServer(mqtt_server, 1883);

  Serial.println("Sistema de Contagem RFID + DHT com MQTT iniciado.");
}

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  if (!mfrc522.PICC_IsNewCardPresent() || !mfrc522.PICC_ReadCardSerial())
    return;

  String uid = lerUID(mfrc522.uid);
  DateTime agora = rtc.now();

  int idx = buscarUsuario(uid);
  String acao;

  if (idx == -1) {
    usuarios[totalUsuarios].uid = uid;
    usuarios[totalUsuarios].dentro = true;
    totalUsuarios++;
    contadorPessoas++;
    acao = "ENTRADA";
  } else {
    if (usuarios[idx].dentro) {
      usuarios[idx].dentro = false;
      contadorPessoas--;
      acao = "SAIDA";
    } else {
      usuarios[idx].dentro = true;
      contadorPessoas++;
      acao = "ENTRADA";
    }
  }

  // Feedback sonoro/visual
  digitalWrite(LED_PIN, HIGH);
  tone(BUZZER_PIN, 1000);
  delay(300);
  noTone(BUZZER_PIN);
  digitalWrite(LED_PIN, LOW);

  // Leitura do DHT
  float temperatura = dht.readTemperature();
  float umidade = dht.readHumidity();

  // Monta mensagem JSON para enviar ao Node-RED
  String mensagem = "{";
  mensagem += "\"uid\":\"" + uid + "\",";
  mensagem += "\"acao\":\"" + acao + "\",";
  mensagem += "\"contador\":" + String(contadorPessoas) + ",";
  mensagem += "\"hora\":\"" + String(agora.hour()) + ":" + String(agora.minute()) + ":" + String(agora.second()) + "\",";
  mensagem += "\"temp\":" + String(temperatura, 1) + ",";
  mensagem += "\"umid\":" + String(umidade, 1);
  mensagem += "}";

  // Envia via MQTT
  client.publish("sala/rfid", mensagem.c_str());

  Serial.println("Enviado: " + mensagem);

  mfrc522.PICC_HaltA();
  mfrc522.PCD_StopCrypto1();
}
