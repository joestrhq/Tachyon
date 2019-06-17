/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.utils;

import net.md_5.bungee.api.ProxyServer;
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
public class RequestListener implements IMqttMessageListener {

    String ipAddress;
    Integer port;
    String topic;
    Integer qos;
    String clientId;
    MemoryPersistence persistence;

    public RequestListener(String ipAddress, Integer port, String topic, Integer qos, String clientId, MemoryPersistence persistence) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.topic = topic;
        this.qos = qos;
        this.clientId = clientId;
        this.persistence = persistence;
    }
    
    public void startListening() {
        
        try {
            MqttClient sampleClient =
                new MqttClient(
                    "tcp://" + ipAddress + ":" + port,
                    clientId,
                    persistence
                );
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + ipAddress);
            sampleClient.connect(connOpts);
            
            System.out.println("Connected");
            
            sampleClient.subscribe(topic, qos, this);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
    
    @Deprecated
    public void setAllVariables() {
        ipAddress = "127.0.0.1";
        port = 1883;
        topic = ProxyServer.getInstance().getName();
        qos = 2;
        clientId = "Tachyon-BungeeCord-" + ProxyServer.getInstance().getName();
        persistence = new MemoryPersistence();
    }

    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        
    }
}
