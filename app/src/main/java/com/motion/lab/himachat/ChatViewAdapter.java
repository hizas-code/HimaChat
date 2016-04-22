package com.motion.lab.himachat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PERSONAL on 4/14/2016.
 */
public class ChatViewAdapter extends BaseAdapter {
    Context context;
    List<Chat> chats = new ArrayList<>();
    String username;

    public ChatViewAdapter(Context context, List<Chat> chats, String username) {
        this.context = context;
        this.chats = chats;
        this.username = username;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int type = getItemViewType(position);
        if (v == null) {
// Inflate the layout according to the view type
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (type == 0) {
// Inflate the layout with image
                v = inflater.inflate(R.layout.list_row_me, parent, false);
            }
            else {
                v = inflater.inflate(R.layout.list_row_user, parent, false);
            }
        }

        Chat c = chats.get(position);

        if (type == 0) {
            TextView chat = (TextView) v.findViewById(R.id.chatRowMe);
            TextView time = (TextView) v.findViewById(R.id.timeRowMe);

            chat.setText(c.getChat());
            time.setText(c.getDate());
        }else{
            TextView username = (TextView) v.findViewById(R.id.userRowUser);
            TextView chat = (TextView) v.findViewById(R.id.chatRowUser);
            TextView time = (TextView) v.findViewById(R.id.timeRowUser);
            ImageView image = (ImageView) v.findViewById(R.id.imageRowUser);

            username.setText(c.getUsername());
            chat.setText(c.getChat());
            time.setText(c.getDate());
            if(c.getSex()==0){
                image.setImageResource(R.drawable.man);
            } else {
                image.setImageResource(R.drawable.woman);
            }
        }
        return v;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (chats.get(position).getUsername().equals(username)) ? 0 : 1;
    }

}
