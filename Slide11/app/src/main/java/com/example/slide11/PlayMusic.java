package com.example.slide11;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

public class PlayMusic extends Service {
  MediaPlayer mediaPlayer;
  public PlayMusic() {
  }
  private final IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
    @Override
    public String getSongName() throws RemoteException {
      return null;
    }

    @Override
    public void changeMediaStatus() throws RemoteException {

    }

    @Override
    public void playSong() throws RemoteException {

    }

    @Override
    public void play() {
      mediaPlayer = MediaPlayer.create(PlayMusic.this, R.raw.loli_requiem);
      mediaPlayer.start();
    }

    @Override
    public void pause() throws RemoteException {
      mediaPlayer.release();
      mediaPlayer = null;
    }

    @Override
    public int getCurrentDuration() throws RemoteException {
      return 0;
    }

    @Override
    public int getTotalDuration() throws RemoteException {
      return 0;
    }
  };

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    return binder;
  }
}