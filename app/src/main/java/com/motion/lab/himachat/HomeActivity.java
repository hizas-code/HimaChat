package com.motion.lab.himachat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.motion.lab.himachat.network.NetHelper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!AppConfig.isLogin(HomeActivity.this)){
            Intent move = new Intent(this, LoginActivity.class);
            startActivity(move);
            finish();
        }

//        LinearLayout parent = (LinearLayout)findViewById(R.id.parent_view);
//
//        for (int i = 0; i < 4; i++) {
//            getLayoutInflater().inflate(R.layout.item_menu, parent);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if (id == R.id.action_sign_out) {
            // Sign out
            Intent move = new Intent(this, LoginActivity.class);
            startActivity(move);
            AppConfig.saveLogin(HomeActivity.this, NetHelper.SINGED_OUT);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
