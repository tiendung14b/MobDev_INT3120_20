package com.example.slide11;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import android.os.Process;

import androidx.core.app.NotificationCompat;

public class HelloService extends Service {

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    try {
      Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
      NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      NotificationCompat.Builder builder = new NotificationCompat.Builder(HelloService.this, MainActivity.CHANNEL_ID);
      builder.setContentTitle("My notification");
      builder.setContentText("Hello World!");
      builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
      builder.setSmallIcon(R.drawable.ic_launcher_foreground);
      builder.setProgress(10, 0, false);
      notificationManager.notify(1, builder.build());
      builder.setNotificationSilent();
      startForeground(1, builder.build());
      new Thread(() -> {
        for (int i = 0; i < 10; i++) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          builder.setProgress(10, i, false);
          notificationManager.notify(MainActivity.NOTIFICATION_ID, builder.build());
        }
        builder.setContentText("Hello World! Done!");
        builder.setProgress(0, 0, false);
        notificationManager.notify(MainActivity.NOTIFICATION_ID, builder.build());
      }).start();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return START_NOT_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent) {
    // We don't provide binding, so return null
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
  }
}