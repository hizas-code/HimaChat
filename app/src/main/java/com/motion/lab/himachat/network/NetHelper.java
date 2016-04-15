package com.motion.lab.himachat.network;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.motion.lab.himachat.R;

import cz.msebera.android.httpclient.Header;

/**
 * Created by maaakbar on 4/10/16.
 */
public class NetHelper {
    public static boolean SINGED_IN = true;
    public static boolean SINGED_OUT = false;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void doLogin(Context context, String username, String password,
                               AsyncHttpResponseHandler eventListener) {
        // login url.. lak
        String url = context.getString(R.string.domain_url)+ "/login";

        // request
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        Log.i("Nethelper", url);

        // methode post menggunakan library loopj
        client.post(url, params, eventListener);
    }

    public static void doRegister(Context context,String fullname, String email, String username,
                                  String password, AsyncHttpResponseHandler eventListener) {
        // register url
        String url = context.getString(R.string.domain_url)+"/add_user";

        // request
        RequestParams params = new RequestParams();
        params.put("fullname", fullname);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);

        Log.i("Nethelper", url);

        // methode post menggunakan library loopj
        client.post(url, params, eventListener);
    }
}
