package com.motion.lab.himachat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        ((WebView)findViewById(R.id.terms_text)).loadData(getString(R.string.terms_long), mimeType, encoding);
    }
}
