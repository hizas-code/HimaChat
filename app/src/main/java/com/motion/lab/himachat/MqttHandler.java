package com.motion.lab.himachat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PERSONAL on 4/21/2016.
 */
public class MqttHandler {
    final String TAG = "MQTT Handler";
    Context context;
    ArrayList<Chat> chats;
    ChatViewAdapter chatViewAdapter;
    MqttCallback callback;

    MqttAndroidClient client;
    final String topic = "HIMACHAT";

    public MqttHandler(Context context, ArrayList<Chat> chats, ChatViewAdapter chatViewAdapter, MqttCallback callback) {
        this.context = context;
        this.chats = chats;
        this.chatViewAdapter = chatViewAdapter;
        this.callback = callback;

        setupMQTT();
    }

    void setupMQTT(){
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, "tcp://10.5.13.125:1883", clientId);
        client.setCallback(callback);
    }

    public void connect(){
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribe(){
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String username, String messageText, int sex, String date) {
        MqttMessage message = new MqttMessage();
        message.setQos(1);
        String messagePayLoad = "{\"username\":\"" + username + "\",\"text\":\"" + messageText + "\",\"sex\":" + sex + ",\"date\":\"" + date +"\"}";

        message.setPayload((messagePayLoad).getBytes());
        try {
            client.publish(topic, messagePayLoad.getBytes(),1 ,true);
            //client.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error, " + e.getMessage(), Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context,LoginActivity.class));
        }
//
    }
}
