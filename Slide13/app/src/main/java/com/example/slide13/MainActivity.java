package com.example.slide13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
  private FusedLocationProviderClient mFusedLocationClient;
  protected void onCreate(Bundle savedInstanceState) {
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
  }
          mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

    public void onSuccess(Location location) {

      // Lấy vị trí tại thời điểm gần nhất, có thể null

      if (location != null) {

      }
    }

  });
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}