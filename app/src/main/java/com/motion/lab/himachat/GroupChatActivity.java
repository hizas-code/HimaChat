package com.motion.lab.himachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.motion.lab.himachat.network.NetHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener, MqttCallback {
    ArrayList<Chat> chats = new ArrayList<>();
    private String username;

    EditText text;
    Button send, sendOtherUser;

    ChatViewAdapter chatViewAdapter;
    ListView listView;

    MqttHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_group_chat);

        username = AppConfig.getLoggedUser(GroupChatActivity.this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // cek apakah sudah login
        // tanda seru (!) menandakan not, jadi jika belum login
        if (!AppConfig.isLogin(GroupChatActivity.this)){
            Intent move = new Intent(GroupChatActivity.this, LoginActivity.class);
            startActivity(move);
            // finish supaya tidak bisa di back ke halaman home
            finish();
        }

        text = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.button);

        sendOtherUser = (Button) findViewById(R.id.butOtherUser);

        handler = new MqttHandler(getApplicationContext(),chats,chatViewAdapter, this);
        handler.connect();
//
//        Chat chat1 = new Chat("rahmatridham", "hoihoi", 1);
//        chats.add(chat1);
//        Chat chat2 = new Chat("wet", "weeeeeewwwww", 0);
//        chats.add(chat2);
//        Chat chat3 = new Chat("wew", "rerdfghfdh", 1);
//        chats.add(chat3);
//        Chat chat4 = new Chat("rahmatridham", "dfdhjfg", 1);
//        chats.add(chat4);
//        Chat chat5 = new Chat("wet", "hoisdfhoi", 0);
//        chats.add(chat5);
//        Chat chat6 = new Chat("rahmatridham", "hoihosdfsdfi", 1);
//        chats.add(chat6);

        listView = (ListView) findViewById(R.id.listView);
        chatViewAdapter = new ChatViewAdapter(this, chats, username);
        listView.setAdapter(chatViewAdapter);

        send.setOnClickListener(this);
        sendOtherUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //tombol user
            case R.id.button:
                String messageText = (String) text.getText().toString();
                text.setText("");

//                if (messageText.equals("bye")) {
//                    handler.disconnect();
//                    startActivity(new Intent(this, LoginActivity.class));
//                }
//
//                handler.publish(username,messageText,1);


                Chat chat = new Chat(username, messageText, 0);
                handler.publish(username, messageText, 0, chat.getDate());
//                chats.add(chat);

//                chatViewAdapter.notifyDataSetChanged();
//                scrollMyListViewToBottom();
                break;

            //tombol other user
            case R.id.butOtherUser:
                String messageTexti = (String) text.getText().toString();
                text.setText("");
                Chat chatzi = new Chat("Alif", messageTexti, 1);
                chats.add(chatzi);

                chatViewAdapter.notifyDataSetChanged();
                scrollMyListViewToBottom();
                break;


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
        Toast.makeText(this, "Connection Lost, " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        try {
            JSONObject object = new JSONObject(mqttMessage.toString());
            Log.i("Chat", mqttMessage.toString());
            Chat chat = new Chat(object.get("username").toString(), object.get("text").toString(), object.getInt("sex"));
            chats.add(chat);
            chatViewAdapter.notifyDataSetChanged();
            scrollMyListViewToBottom();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // bikin menu
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // kalau menu sign out ditekan
        if (id == R.id.action_sign_out) {
            // Sign out
            Intent move = new Intent(this, LoginActivity.class);
            startActivity(move);
            AppConfig.saveLogin(GroupChatActivity.this, NetHelper.SINGED_OUT);
            AppConfig.saveAccount(GroupChatActivity.this, "", "");
            // finish supaya tidak bisa di back ke halaman home
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
