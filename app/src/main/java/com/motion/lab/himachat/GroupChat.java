package com.motion.lab.himachat;

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
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;

//import com.example.personal.partchat.R;

public class GroupChat extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Chat> chats = new ArrayList<>();
    static String username = "rahmatridham";

    EditText text;
    Button send;

    MqttConnectOptions options;

    ChatViewAdapter chatViewAdapter;
    ListView listView;

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

//        //Generate client id
//        String clientId = MqttClient.generateClientId();
//        MqttClient client =
//                new MqttClient("tcp://broker.hivemq.com:1883", clientId);
//
//        //create mqtt connection
//        try {
//            IMqttToken token = client.connect();
//            token.setActionCallback(new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    // We are connected
//                    Log.d("Status", "onSuccess");
//                    Toast.makeText(GroupChat.this, "Connection Success", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    // Something went wrong e.g. connection timeout or firewall problems
//                    Log.d("Status", "onFailure");
//                    Toast.makeText(GroupChat.this, "Connection Fail," + "\n" + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//
//        options = new MqttConnectOptions();
////        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
//        try {
//            IMqttToken token = client.connect(options);
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//
//
//        String topic = "users/last/will";
//        byte[] payload = "some payload".getBytes();
//        options.setWill(topic, payload, 1, false);
//        try {
//            IMqttToken token = client.connect(options);
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String message = (String) text.getText().toString();
                text.setText("");
                Chat chat = new Chat(username,message,1);
                chats.add(chat);
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
}
