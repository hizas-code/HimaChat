package com.motion.lab.himachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.example.personal.partchat.R;

public class GroupChat extends AppCompatActivity implements View.OnClickListener, MqttCallback {
    ArrayList<Chat> chats = new ArrayList<>();
    static String username = "rahmatridham";

    EditText text;
    Button send;

    ChatViewAdapter chatViewAdapter;
    ListView listView;

    MqttConnectOptions options;
    MqttClient subscribe, read;

    MqttMessage message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_group_chat);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        text = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.button);

        Chat chat1 = new Chat("rahmatridham", "hoihoi", 1);
        chats.add(chat1);
        Chat chat2 = new Chat("wet", "weeeeeewwwww", 0);
        chats.add(chat2);
        Chat chat3 = new Chat("wew", "rerdfghfdh", 1);
        chats.add(chat3);
        Chat chat4 = new Chat("rahmatridham", "dfdhjfg", 1);
        chats.add(chat4);
        Chat chat5 = new Chat("wet", "hoisdfhoi", 0);
        chats.add(chat5);
        Chat chat6 = new Chat("rahmatridham", "hoihosdfsdfi", 1);
        chats.add(chat6);

        listView = (ListView) findViewById(R.id.listView);
        chatViewAdapter = new ChatViewAdapter(this, chats);
        listView.setAdapter(chatViewAdapter);

        send.setOnClickListener(this);

        try {
            options = new MqttConnectOptions();
            options.setCleanSession(false);

            subscribe = new MqttClient("tcp://localhost:1883", "Sender");
            read = new MqttClient("tcp://localhost:1883", "Reciever");
            subscribe.connect(options);
            read.connect();
            subscribe.setCallback(this);
            read.subscribe("HIMACHAT", 2);

        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String messageText = (String) text.getText().toString();
                text.setText("");
                if (messageText.equals("bye")) {
                    try {
                        read.disconnect();
                        subscribe.disconnect();
                        read.close();
                        subscribe.close();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(this, LoginActivity.class));
                }
                Chat chat = new Chat(username, messageText, 1);
                chats.add(chat);

                message = new MqttMessage();
                String messagePayLoad = "{\"username\":\"" + username + "\",\"text\":\"" + messageText + "\",\"sex\":" + 1 + "}";

                message.setPayload((messagePayLoad).getBytes());
                try {
                    subscribe.publish("HIMACHAT", message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                chatViewAdapter.notifyDataSetChanged();
                scrollMyListViewToBottom();
        }
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(listView.getCount() - 1);
            }
        });
    }

    @Override
    public void connectionLost(Throwable throwable) {
        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(GroupChat.this, "Connection Lost, "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        JSONObject object = new JSONObject(mqttMessage.toString());
        Chat chat = new Chat(object.get("username").toString(),object.get("text").toString(),object.getInt("sex"));
        chats.add(chat);
        chatViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}
