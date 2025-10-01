#include <WiFi.h>
#include <PubSubClient.h>
#include <SPI.h>
#include <MFRC522.h>
#include "DHT.h"

// --- DHT ---
#define DHTPIN 27       
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

// --- RFID ---
#define SS_PIN   5
#define RST_PIN  22
MFRC522 mfrc522(SS_PIN, RST_PIN);  
// (SCK=18, MOSI=23, MISO=19)

// --- LEDS e buzzer ---
#define LED_VERMELHO 13
#define LED_VERDE    12
#define BUZZER_PIN   26

// --- WiFi ---
const char* ssid = "Edu";
const char* password = "Eduardo1902";

// --- MQTT (HiveMQ público) ---
const char* mqtt_server = "broker.hivemq.com";
WiFiClient espClient;
PubSubClient client(espClient);

// --- VARIÁVEIS ---
int contadorPessoas = 0;
unsigned long ultimoBlink = 0; //para piscar o vermelho quando estiver em temperaturas altas
bool estadoLed = false; // para piscar o vermelho 

struct Usuario {
  String uid;
  bool dentro;
};
Usuario usuarios[50];
int totalUsuarios = 0;

// ---------------------------- Funções ----------------------------
void setup_wifi() {
  delay(10);
  Serial.println("Conectando ao WiFi...");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWiFi conectado");

  // Configura NTP (fuso horário do Brasil: GMT-3)
  configTime(-3 * 3600, 0, "pool.ntp.org", "time.nist.gov");
  Serial.println("Sincronizando horário via NTP...");
  delay(2000);
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
//converte o UID do cartão em string hexadecimal.
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

String getHoraAtual() {
  struct tm timeinfo;
  if (!getLocalTime(&timeinfo)) {
    return "00:00:00";
  }
  char buffer[10];
  sprintf(buffer, "%02d:%02d:%02d", timeinfo.tm_hour, timeinfo.tm_min, timeinfo.tm_sec);
  return String(buffer);
}

// ---------------------------- Setup ----------------------------
void setup() {
  Serial.begin(115200);

  pinMode(LED_VERMELHO, OUTPUT);
  pinMode(LED_VERDE, OUTPUT);
  pinMode(BUZZER_PIN, OUTPUT);

  // LED vermelho sempre começa ligado
  digitalWrite(LED_VERMELHO, HIGH);

  // Inicializa SPI e RFID
  SPI.begin(18, 19, 23);  
  mfrc522.PCD_Init();

  // DHT
  dht.begin();

  // WiFi + MQTT
  setup_wifi();
  client.setServer(mqtt_server, 1883);

  Serial.println("Sistema de Contagem RFID + DHT com MQTT iniciado.");
}

// ---------------------------- Loop ----------------------------
void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  // Leitura do DHT
  float temperatura = dht.readTemperature();
  float umidade = dht.readHumidity();

  // ---- Alerta de temperatura alta ----
  if (!isnan(temperatura)) {
    if (temperatura > 45.0) {
      // Buzzer contínuo
      tone(BUZZER_PIN, 1500);

      // LED vermelho piscando (500 ms)
      unsigned long agora = millis();
      if (agora - ultimoBlink >= 500) {
        ultimoBlink = agora;
        estadoLed = !estadoLed;
        digitalWrite(LED_VERMELHO, estadoLed ? HIGH : LOW);
      }

    } else {
      // Normal: buzzer desligado + vermelho fixo
      noTone(BUZZER_PIN);
      digitalWrite(LED_VERMELHO, HIGH);
    }
  }

  // ---- RFID ----
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    String uid = lerUID(mfrc522.uid);
    String horaAtual = getHoraAtual();

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
    digitalWrite(LED_VERDE, HIGH);
    digitalWrite(LED_VERMELHO, LOW);
    tone(BUZZER_PIN, 1000);
    delay(300);
    noTone(BUZZER_PIN);
    digitalWrite(LED_VERDE, LOW);
    digitalWrite(LED_VERMELHO, HIGH);

    // Monta mensagem JSON
    String mensagem = "{";
    mensagem += "\"uid\":\"" + uid + "\",";
    mensagem += "\"acao\":\"" + acao + "\",";
    mensagem += "\"contador\":" + String(contadorPessoas) + ",";
    mensagem += "\"hora\":\"" + horaAtual + "\",";
    mensagem += "\"temp\":" + String(temperatura, 1) + ",";
    mensagem += "\"umid\":" + String(umidade, 1);
    mensagem += "}";

    client.publish("sala/rfid", mensagem.c_str());
    Serial.println("Enviado: " + mensagem);

    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
  }
}