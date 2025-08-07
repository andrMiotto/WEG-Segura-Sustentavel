package weg.seguranca.service;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class MqttService {

    @Autowired
    private MqttClient mqttClient;

    public void subscribeToTopic(String topic) throws MqttException {
        mqttClient.subscribe(topic, (t, message) -> {
            String payload = new String(message.getPayload());
            processSensorData(payload);
        });
    }

    private void processSensorData(String payload) {
        // Implementar lógica de processamento
    }
}
