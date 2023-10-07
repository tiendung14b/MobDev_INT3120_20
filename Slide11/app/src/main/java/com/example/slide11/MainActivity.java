package com.example.slide11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
  public static final String CHANNEL_ID = "hello_service_channel";
  public static final int NOTIFICATION_ID = 1;
  public NotificationManager notificationManager;
  public NotificationCompat.Builder builder;
  public NotificationChannel channel;
  BoundService boundService;
  private ServiceConnection serviceConnection;
  private ServiceConnection aidlServiceConnection;
  BoundService.MyBinder binder;
  IMyAidlInterface iMyAidlInterface;
  boolean isplay = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    builder = new NotificationCompat.Builder(this, CHANNEL_ID);
    serviceConnection = new ServiceConnection() {
      @Override
      public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        binder = (BoundService.MyBinder) iBinder;
        boundService = binder.getService();
        boundService.startMusic();
        Toast.makeText(MainActivity.this, "service connected", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onServiceDisconnected(ComponentName componentName) {
        Toast.makeText(MainActivity.this, "service disconnected", Toast.LENGTH_SHORT).show();
      }
    };

    aidlServiceConnection = new ServiceConnection() {
      @Override
      public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        try {
          iMyAidlInterface.play();
        } catch (RemoteException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public void onServiceDisconnected(ComponentName componentName) {

      }
    };
  }

  public void onPlayMusic(View view) {
    startService(new Intent(this, BackgroundService.class));
  }

  private void askForNotifyPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID,  CHANNEL_ID);
        startActivity(intent);
      }
    }
  }

  public void onStopAIDL(View view) throws RemoteException {
    if (isplay) {
      iMyAidlInterface.pause();
    } else {
      iMyAidlInterface.playSong();
    }
  }
  private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is not in the Support Library.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "Hello Service Channel";
      String description = "Channel for Hello Service";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      channel = new NotificationChannel(CHANNEL_ID, name, importance);
      channel.setDescription(description);
      // Register the channel with the system. You can't change the importance
      // or other notification behaviors after this.
      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

  public void onStartService(View view) {
    createNotificationChannel();
    askForNotifyPermission();
    ContextCompat.startForegroundService(this, new Intent(this, HelloService.class));
  }
  public void onStartBoundService(View view) {
    bindService(new Intent(this, BoundService.class), serviceConnection, BIND_AUTO_CREATE);
    Toast.makeText(this, "start bound service", Toast.LENGTH_SHORT).show();
  }

  public void onAIDLService(View view) {
    try {
      bindService(new Intent(this, PlayMusic.class), aidlServiceConnection, BIND_AUTO_CREATE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  public void onStopBoundService(View view) {
    unbindService(serviceConnection);
  }
}