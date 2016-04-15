package com.motion.lab.himachat;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.motion.lab.himachat.network.NetHelper;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    // set variable
    TextInputLayout eFullname, eMail, eUsername, ePassword;
    CheckBox cAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // cari view
        eFullname = (TextInputLayout)findViewById(R.id.fullname_text);
        eMail = (TextInputLayout)findViewById(R.id.mail_text);
        eUsername = (TextInputLayout)findViewById(R.id.username_text);
        ePassword = (TextInputLayout)findViewById(R.id.password_text);

        cAccept = (CheckBox)findViewById(R.id.terms_box);

        // set on click
        View buttonAccept = findViewById(R.id.register_button);
        if (buttonAccept != null)
            buttonAccept.setOnClickListener(this);

        View term = findViewById(R.id.terms_text);
        if (term != null)
            term.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // pilih operasi button
        switch (v.getId()){
            case R.id.terms_text:
                Intent move = new Intent(this, TermsActivity.class);
                startActivity(move);
                break;
            case R.id.register_button:
                // cek input kosong atau tidak
                if (eFullname!= null && !eFullname.getEditText().getText().toString().isEmpty()){
                    if (eMail!= null && !eMail.getEditText().getText().toString().isEmpty()){
                        if (eUsername!= null && !eUsername.getEditText().getText().toString().isEmpty()){
                            if (ePassword!= null && !ePassword.getEditText().getText().toString().isEmpty()){
                                if (cAccept!= null && cAccept.isChecked()){
                                    // lakukan register
                                    NetHelper.doRegister(RegisterActivity.this,
                                            eFullname.getEditText().getText().toString(),
                                            eMail.getEditText().getText().toString(),
                                            eUsername.getEditText().getText().toString(),
                                            ePassword.getEditText().getText().toString(), httpResponseHandler);
                                }else
                                    Toast.makeText(RegisterActivity.this,
                                            "Please accept the terms and conditions",
                                            Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(RegisterActivity.this, "Please fill your password",
                                        Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(RegisterActivity.this, "Please fill your username",
                                    Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(RegisterActivity.this, "Please fill your email",
                                Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(RegisterActivity.this, "Please fill your full name",
                            Toast.LENGTH_SHORT).show();

                break;
        }
    }

    AsyncHttpResponseHandler httpResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            // kalau berhasil
            Toast.makeText(RegisterActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };
}
