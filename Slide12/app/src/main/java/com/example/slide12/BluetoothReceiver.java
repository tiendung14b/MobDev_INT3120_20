package com.example.slide12;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.Objects;

public class BluetoothReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Objects.equals(intent.getAction(), BluetoothDevice.ACTION_FOUND)) {
      BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
      if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        return;
      }
      assert device != null;
      String name = device.getName();
      String address = device.getAddress();
      int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
      System.out.println("Name: " + name + " Address: " + address + " RSSI: " + rssi);
    } else if (Objects.equals(intent.getAction(), BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
      System.out.println("Finished");
    }
  }
}