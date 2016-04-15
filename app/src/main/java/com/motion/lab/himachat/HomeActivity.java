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

        // cek apakah sudah login
        // tanda seru (!) menandakan not, jadi jika belum login
        if (!AppConfig.isLogin(HomeActivity.this)){
            Intent move = new Intent(this, LoginActivity.class);
            startActivity(move);
            // finish supaya tidak bisa di back ke halaman home
            finish();
        }
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
            AppConfig.saveLogin(HomeActivity.this, NetHelper.SINGED_OUT);
            AppConfig.saveAccount(HomeActivity.this, "", "");
            // finish supaya tidak bisa di back ke halaman home
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
