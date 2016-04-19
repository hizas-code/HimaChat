package com.motion.lab.himachat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PERSONAL on 4/14/2016.
 */
public class Chat {
    String username, chat, date;
    int sex;

    public Chat(String username, String chat, int sex) {
        this.username = username;
        this.chat = chat;
        this.date = date;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
        String currentTime = df.format(Calendar.getInstance().getTime());
        return currentTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
