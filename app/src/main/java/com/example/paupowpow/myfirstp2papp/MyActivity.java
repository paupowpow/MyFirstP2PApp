package com.example.paupowpow.myfirstp2papp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private IntentFilter myIntentFilter;
    private WifiP2pManager.Channel myChannel;
    private BroadcastReceiver myReceiver;
    private WifiP2pManager myManager;
    private boolean isWifiP2pEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myIntentFilter = new IntentFilter();
        // indicates whether WiFi P2P is enabled/disabled
        myIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // indicates that the available peer list has changed
        myIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // indicates the state of WiFi P2P connectivity has changed
        myIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // indicates this device's config details have changed
        myIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        myManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        // A channel that connects the application to the Wifi p2p framework
        // Most p2p operations require a Channel as an argument.
        // register the application with the Wi-Fi P2P framework
        myChannel = myManager.initialize(this, getMainLooper(), null);
        // create BroadcastReceiver
        myReceiver = new MyBroadcastReceiver(myManager, myChannel, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        // we are only interested in receiving a broadcast while running,
        // that's why we don't register it in the AndroidManifest.xml
        registerReceiver(myReceiver, myIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Called when the user clicks the Send button
    public void sendMessage(View view) {
        // Do something
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        // do something in response to the boolean argument
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(myReceiver);
    }
}
