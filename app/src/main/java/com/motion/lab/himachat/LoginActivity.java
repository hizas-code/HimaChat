package com.motion.lab.himachat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.motion.lab.himachat.network.NetHelper;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eUsername, ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsername = (EditText)findViewById(R.id.username_text);
        ePassword = (EditText)findViewById(R.id.password_text);

        View register = findViewById(R.id.register_button);
        View sign_in = findViewById(R.id.sign_in_button);
        if (register != null && sign_in != null){
            register.setOnClickListener(this);
            sign_in.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                Intent move = new Intent(this, RegisterActivity.class);
                startActivity(move);
                break;
            case R.id.sign_in_button:
                if (eUsername != null && !eUsername.getText().toString().isEmpty()){
                    if (ePassword != null && !ePassword.getText().toString().isEmpty()) {
                        NetHelper.doLogin(LoginActivity.this, eUsername.getText().toString(),
                                ePassword.getText().toString(), httpResponseHandler);
                    }else {
                        Toast.makeText(LoginActivity.this, "Fill your password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Fill your username", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                String result = new String(responseBody, "UTF-8");
                switch (result){
                    case "success":
                        Intent move = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(move);
                        AppConfig.saveLogin(LoginActivity.this, NetHelper.SINGED_IN);
                        finish();
                        break;
                    case "user":
                        Toast.makeText(LoginActivity.this, "Your username is wrong", Toast.LENGTH_SHORT).show();
                        break;
                    case "pass":
                        Toast.makeText(LoginActivity.this, "Your password is wrong", Toast.LENGTH_SHORT).show();
                        break;
                    default: break;
                }
            }catch (UnsupportedEncodingException ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            try {
                String result = new String(responseBody, "UTF-8");
                Log.i("Login", "onFailure: "+result);
            }catch (UnsupportedEncodingException ex){
                ex.printStackTrace();
            }
        }
    };
}
