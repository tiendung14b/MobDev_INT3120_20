package com.example.slide8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Toast.makeText(context, "MyReceiver2", Toast.LENGTH_SHORT).show();
    Log.d("MyReceiver2", "MyReceiver2");
  }
}