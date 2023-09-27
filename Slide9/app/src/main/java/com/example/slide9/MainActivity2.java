package com.example.slide9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

  public void onLogout(View view) {
    SharedPreferences sharedPreferences = getSharedPreferences("com.example.slide9", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("isLoggedIn", false);
    editor.apply();
    startActivity(new Intent(this, MainActivity.class));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
  }
}