/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.request;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Joel
 */
public class RequestManager implements IMqttMessageListener {

    String ipAddress;
    Integer port;
    String topic;
    Integer qos;
    String clientId;
    MemoryPersistence persistence;
    MqttClient mqttClient;

    public RequestManager(String ipAddress, Integer port, String topic, Integer qos, String clientId, MemoryPersistence persistence) throws MqttException {
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
                "tcp://" + ipAddress + ":" + port,
                clientId,
                persistence
            );
        
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        
        mqttClient.connect(connOpts);

        mqttClient.subscribe(topic, qos, this);
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        
        new Gson().fromJson(new String(mqttMessage.getPayload()), RequestManager.class);
    }
    
    public void sendMessage(String message) throws MqttException {
        
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(message.getBytes());
        
        this.mqttClient.publish(topic, mqttMessage);
    }
}
