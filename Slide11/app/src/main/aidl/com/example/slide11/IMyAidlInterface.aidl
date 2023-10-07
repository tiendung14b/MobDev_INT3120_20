// IMyAidlInterface.aidl
package com.example.slide11;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    String getSongName();

    void changeMediaStatus();

    void playSong();

    void play();

    void pause();

    int getCurrentDuration();

    int getTotalDuration();
}