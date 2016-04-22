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

// telah mengimplement click listener
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // penampung edit text
    EditText eUsername, ePassword;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // mencari view pada layout .xml
        eUsername = (EditText)findViewById(R.id.username_text);
        ePassword = (EditText)findViewById(R.id.password_text);

        // mecari view sekaligus memberikan fungsi button
        View register = findViewById(R.id.register_button);
        View sign_in = findViewById(R.id.sign_in_button);
        if (register != null && sign_in != null){
            // this nunjuk objek yg memamnggil fungsi ini
            register.setOnClickListener(this);
            sign_in.setOnClickListener(this);
        }
    }

    // methode async handler
    AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        // dilakukan ketika login berhasil ke server
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                // terima response dari server
                String result = new String(responseBody, "UTF-8");
                switch (result){
                    // kalau respons sukses
                    case "success":
                        Intent move = new Intent(LoginActivity.this, GroupChatActivity.class);
                        startActivity(move);
                        // status login disimpan
                        AppConfig.saveLogin(LoginActivity.this, NetHelper.SINGED_IN);
                        AppConfig.saveAccount(LoginActivity.this, username, password);
                        // finish, supaya kalau ditekan tombol back tidak kembali kehalaman login
                        finish();
                        break;
                    // kalau respons password salah
                    case "pass":
                        Toast.makeText(LoginActivity.this, "Your password is wrong", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "Your username is wrong", Toast.LENGTH_SHORT).show();
                        break;
                }
            }catch (UnsupportedEncodingException ex){
                ex.printStackTrace();
            }
        }

        // dilakukan ketika login gagal ke server
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//            try {
                // String result = new String(responseBody, "UTF-8");
                Log.i("Login", "onFailure: "+statusCode);
                Toast.makeText(LoginActivity.this, "Your username is wrong", Toast.LENGTH_SHORT).show();
//            }catch (UnsupportedEncodingException ex){
//                ex.printStackTrace();
//            }
        }
    };

    // kalau mengimplement, semua methode pada interface View.OnClickListener harus di define.
    // yaitu OnClicl
    // tekan alt + insert
    // pilih override
    @Override
    public void onClick(View v) {
        // karena ada 2 button yg perlu di handle perlu ada pemisah
        switch (v.getId()){ // sama aja degan case ... of ..
            case R.id.register_button:
                // kalau click register dia pindah halaman ke register
                Intent move = new Intent(this, RegisterActivity.class);
                startActivity(move);
                break;
            case R.id.sign_in_button:
                // kalau click sign in
                // cek apakah username dan password sudah diisi
                if (eUsername != null && !eUsername.getText().toString().isEmpty()){
                    if (ePassword != null && !ePassword.getText().toString().isEmpty()) {
                        // simpan sementara username dan password
                        username = eUsername.getText().toString();
                        password = ePassword.getText().toString();
                        // kalau terisi lakukan login
                        NetHelper.doLogin(LoginActivity.this, eUsername.getText().toString(),
                                ePassword.getText().toString(), httpResponseHandler);
                    }else {
                        // kalau kosong password, suruh user isi password
                        Toast.makeText(LoginActivity.this, "Fill your password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    // kalau kosong password, suruh user isi username
                    Toast.makeText(LoginActivity.this, "Fill your username", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
