package com.example.slide12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

  private static String getNetworkInfo(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo != null) {
      return "connected";
    }
    return "disconnected";
  }

  public ConnectivityChangedReceiver() {
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    String status = getNetworkInfo(context);
    Toast.makeText(context, "Network status: " + status, Toast.LENGTH_SHORT).show();
  }
}