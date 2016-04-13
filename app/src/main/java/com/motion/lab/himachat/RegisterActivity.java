package com.motion.lab.himachat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View term = findViewById(R.id.terms_text);
        View done = findViewById(R.id.register_button);
        if (term != null)
            term.setOnClickListener(this);
        if (done != null)
            done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent move;
        switch (v.getId()){
            case R.id.terms_text:
                move = new Intent(this, TermsActivity.class);
                startActivity(move);
                break;
            case R.id.register_button:
                finish();
                break;
        }
    }
}
