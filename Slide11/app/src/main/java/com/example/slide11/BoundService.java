package com.example.slide11;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BoundService extends Service {
  MediaPlayer player;
  MyBinder myBinder = new MyBinder();
  public class MyBinder extends Binder {
    BoundService getService() {
      return BoundService.this;
    }
  }

  public void startMusic() {
    player = MediaPlayer.create(this, R.raw.loli_requiem);
    player.setLooping(true);
    player.start();
  }

  public BoundService() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Toast.makeText(this, "create bound service", Toast.LENGTH_SHORT).show();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return myBinder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    player.release();
    player = null;
  }
}