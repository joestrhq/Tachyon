/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.request;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final RequestManager instance = this;
    
    private final Set<RequestEvent<? extends Request>> listeners;
    
    String ipAddress;
    Integer port;
    String topic;
    Integer qos;
    String clientId;
    MemoryPersistence persistence;
    MqttClient mqttClient;

    /**
     * Creates a new instance of a request manager.
     * 
     * @param ipAddress The IP address of the broker
     * @param port The port of the broker
     * @param topic The topic to use
     * @param qos The quality of service
     * @param clientId The id of the client
     * @param persistence The MemoryPersistence
     * @throws MqttException If the initialization fails
     */
    public RequestManager(String ipAddress, Integer port, String topic, Integer qos, String clientId, MemoryPersistence persistence) throws MqttException {
        
        this.listeners = new HashSet<>();
        
        this.ipAddress = ipAddress;
        this.port = port;
        this.topic = topic;
        this.qos = qos;
        this.clientId = clientId;
        this.persistence = persistence;
        
        this.configureMqttClient();
    }
    
    /**
     * Configures the MQTT client. This method puts the listener into a separate
     * thread.
     * 
     * @throws MqttException 
     */
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

        mqttClient.subscribe(topic, qos, instance);
    }
    
    /**
     * Handles incoming messages.
     * 
     * @param topic The topic
     * @param mqttMessage The actual message
     * @throws Exception If something fails
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        
        String message = new String(mqttMessage.getPayload());
        Request request = null;
        
        for(RequestEvent<? extends Request> requestEvent : this.listeners) {
            try {
                request = ((Request) requestEvent.getClass().getGenericSuperclass()).convertStringToRequest(message);
            } catch (Exception ex) {
                request = null;
            }
            
            if (request != null) {
                requestEvent.onRequestReceived(request);
            }
        }
        
    }
    
    /**
     * Send a message to the topic specified in the request maanger itself.
     * 
     * @param message The message to publish
     * @throws MqttException If somethign MQTT relevant fails
     */
    public void sendMessage(String message) throws MqttException {
        
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(this.qos);
        mqttMessage.setPayload(message.getBytes());
        
        this.mqttClient.publish(this.topic, mqttMessage);
    }
    
    /**
     * Send a message to the provided topic in the request maanger itself.
     * 
     * @param topic The topic to send the message at
     * @param message The message to publish
     * @throws MqttException If somethign MQTT relevant fails
     */
    public void sendMessage(String topic, String message) throws MqttException {
        
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(this.qos);
        mqttMessage.setPayload(message.getBytes());
        
        this.mqttClient.publish(topic, mqttMessage);
    }
    
    /**
     * Register a listener.
     * 
     * @param listener THe listener to register.
     * @return 
     */
    public boolean registerListener(RequestEvent<? extends Request> listener) {
        
        return this.listeners.add(listener);
    }
    
    /**
     * Unregister a listener.
     * 
     * @param listener The listener to unregister.
     * @return 
     */
    public boolean unregisterListener(RequestEvent<? extends Request> listener) {
        
        return this.listeners.remove(listener);
    }
    
    /**
     * Get an immutable list of all listners registered to this request manager.
     * 
     * @return A {@link Collection} of all {@link RequestEvents}s reigstered.
     */
    public Collection<RequestEvent> getListeners() {
        return
            new ImmutableList.Builder<RequestEvent>()
                .addAll(this.listeners)
                .build();
    }
}
