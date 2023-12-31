package com.example.slide8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      Toast.makeText(context, "MyReceiver", Toast.LENGTH_SHORT).show();
    }
  }
  MyReceiver2 myReceiver2;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    IntentFilter intentFilter = new IntentFilter("android.intent.action.MY_ACTION");
    MyReceiver myReceiver = new MyReceiver();
    registerReceiver(myReceiver, intentFilter);

    IntentFilter filter = new IntentFilter("android.intent.action.MY_ACTION2");
    myReceiver2 = new MyReceiver2();
    registerReceiver(myReceiver2, filter);
  }

  public void onSendBroadcast(View view) {
    Intent intent = new Intent("android.intent.action.MY_ACTION");
    sendBroadcast(intent);
  }

  public void onSendBroadcast2(View view) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.MY_ACTION2");
    sendBroadcast(intent);
  }

  @Override
  protected void onStop() {
    super.onStop();
    unregisterReceiver(myReceiver2);
  }
}