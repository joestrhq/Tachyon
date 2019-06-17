/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.request;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Joel
 */
public class RequestListener {

    String ipAddress;
    Integer port;
    String topic;
    Integer qos;
    String clientId;
    MemoryPersistence persistence;
    MqttClient mqttClient;
    
    public RequestListener(String ipAddress, Integer port, String topic, Integer qos, String clientId, MemoryPersistence persistence) throws MqttException {
        this.ipAddress = ipAddress;
        this.port = port;
        this.topic = topic;
        this.qos = qos;
        this.clientId = clientId;
        this.persistence = persistence;
        
        this.configureMqttClient();
    }
    
    private void configureMqttClient() throws MqttException {
        this.mqttClient =
            new MqttClient(
                "tcp://" + this.ipAddress + ":" + this.port,
                this.clientId,
                this.persistence
            );

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        this.mqttClient.connect(connOpts);
    }

    public void sendMessage(String message) throws MqttException {
        
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(message.getBytes());
        
        this.mqttClient.publish(topic, mqttMessage);
    }
}
