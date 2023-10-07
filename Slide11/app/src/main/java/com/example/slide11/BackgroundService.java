package com.example.slide11;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundService extends Service {
  private MediaPlayer player;


  public BackgroundService() {

  }

  private void startMusic() {
    player = MediaPlayer.create(this, R.raw.loli_requiem);
    player.setLooping(true);
    player.start();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    startMusic();
    Toast.makeText(this, "start bg", Toast.LENGTH_SHORT).show();
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    player.stop();
    Toast.makeText(this, "destroy bg", Toast.LENGTH_SHORT).show();
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }
}