package com.example.slide12;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
  BroadcastReceiver connectivityChangedReceiver;
  private final BroadcastReceiver wifiStateChangedReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
      switch (wifiStateExtra) {
        case WifiManager.WIFI_STATE_ENABLED:
          ((TextView) findViewById(R.id.wifi)).setText("Wifi is enabled");
          break;
        case WifiManager.WIFI_STATE_DISABLED:
          ((TextView) findViewById(R.id.wifi)).setText("Wifi is disabled");
          break;
      }
    }
  };

  private final BroadcastReceiver bluetoothStateChangedReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        return;
      }
      if (bluetoothAdapter.enable()) {
        ((TextView) findViewById(R.id.bluetooth)).setText("Bluetooth is enabled");
      } else {
        ((TextView) findViewById(R.id.bluetooth)).setText("Bluetooth is disabled");
      }
    }
  };

  private SensorManager sensorManager;
  Sensor accelerometer;
  Sensor light;
  int maxX;
  int maxY;

  private void requestCameraPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
    }
  }

  private void requestSmsPermission() {
    String permission = Manifest.permission.RECEIVE_SMS;
    int grant = ContextCompat.checkSelfPermission(this, permission);
    if (grant != PackageManager.PERMISSION_GRANTED) {
      String[] permission_list = new String[1];
      permission_list[0] = permission;
      ActivityCompat.requestPermissions(this, permission_list, 1);
    }
  }

  private void requestSendPermission() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
        Log.d("permission", "permission denied to SEND_SMS - requesting it");
        String[] permissions = {Manifest.permission.SEND_SMS};
        requestPermissions(permissions, 1);
      }
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.S)
  private void requestBluetoothPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.S)
  private void requestScanBluetoothPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, 1);
        } else {
          //acess fine location
          requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_CONNECT}, 1);
        }
      } else {
        // coarse location
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH_CONNECT}, 1);
      }
    }     
  }

  private void registerBroadcastReceiver() {
    connectivityChangedReceiver = new ConnectivityChangedReceiver();
    registerReceiver(connectivityChangedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    registerReceiver(wifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    registerReceiver(new PhoneStateChangedReceiver(), new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
    registerReceiver(new MySMSReceiver(), new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    registerReceiver(bluetoothStateChangedReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    registerReceiver(new BluetoothReceiver(), new IntentFilter(BluetoothDevice.ACTION_FOUND));
  }

  @SuppressLint("CutPasteId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    requestSendPermission();
    requestSmsPermission();
    requestCameraPermission();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      requestScanBluetoothPermission();
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      requestBluetoothPermission();
    }
    registerBroadcastReceiver();

    showWifiId();

    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
      Toast.makeText(this, "The device has no accelerometer!", Toast.LENGTH_SHORT).show();
      return;
    }

    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
//     get the width of screen
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//    conver pixel to dp with dpi = 402
    maxX = displayMetrics.widthPixels;
    maxY = displayMetrics.heightPixels;
  }

  public void onCamera(View view) {
    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 100);
  }

  public void onRecord(View view) {
    try {
      startActivityForResult(new Intent(MediaStore.ACTION_VIDEO_CAPTURE), 101);
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.S)
  public void onDiscover(View view) {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter == null) {
      Toast.makeText(this, "No bluetooth adapter", Toast.LENGTH_SHORT).show();
      return;
    }
    if (!bluetoothAdapter.isEnabled()) {
      Toast.makeText(this, "Bluetooth is disabled", Toast.LENGTH_SHORT).show();
      return;
    }

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(this, "No permission to scan bluetooth", Toast.LENGTH_SHORT).show();
      //      require permission
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);

      return;
    }
    bluetoothAdapter.startDiscovery();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 100) {
      Bundle extras = data.getExtras();
      Bitmap imageBitmap = (Bitmap) extras.get("data");
      ((ImageView) findViewById(R.id.imageView)).setImageBitmap(imageBitmap);
    } else if (requestCode == 101) {
      // get the video
      if (data != null) {
        ((VideoView) findViewById(R.id.videoView)).setVideoURI(data.getData());
        ((VideoView) findViewById(R.id.videoView)).start();
      }
    }
  }

  private void setBtnPos(int x, int y) {
    Button button = findViewById(R.id.btnMove);
    button.setX(button.getX() - x);
    button.setY(button.getY() + y);
    if (button.getX() < 0) button.setX(0);
    if (button.getY() < 0) button.setY(0);
    if (button.getX() > maxX - button.getWidth()) button.setX(maxX - button.getWidth());
    if (button.getY() > maxY - button.getHeight()) button.setY(maxY - button.getHeight());
  }

  public void onSendSms(View view) {
    try {
      SmsManager smsManager = SmsManager.getDefault();
      String sendTo = "191";
      String message = "kttk";
      smsManager.sendTextMessage(sendTo, null, message, null, null);
    } catch (Exception e) {
      Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  private void showWifiId() {
    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(this, "No permission to access wifi", Toast.LENGTH_SHORT).show();
//      require permission
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

    }
    StringBuilder stringBuilder = new StringBuilder();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      List<WifiNetworkSuggestion> wifiNetworkSuggestions = wifiManager.getNetworkSuggestions();
      for (WifiNetworkSuggestion wifiSugges : wifiNetworkSuggestions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          stringBuilder.append(wifiSugges.getWifiSsid()).append("\n");
        }
      }
    }
    if (stringBuilder.length() == 0) {
      ((TextView) findViewById(R.id.list_wifi_configs)).setText("No wifi config");
    }
    ((TextView) findViewById(R.id.list_wifi_configs)).setText(stringBuilder);
  }

  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      float x = sensorEvent.values[0];
      float y = sensorEvent.values[1];
      setBtnPos((int) x, (int) y);
    } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
      float light = sensorEvent.values[0];
      TextView textView = findViewById(R.id.light);
      textView.setText(String.valueOf(light));
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {
  }
}