package com.example.slide9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

  public void onLogin(View view) {
    SharedPreferences sharedPreferences = getSharedPreferences("com.example.slide9", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("isLoggedIn", true);
    editor.apply();
    startActivity(new Intent(this, MainActivity2.class));
  }

  public void onEditFile(View view) throws IOException {
    FileOutputStream fos = null;
    EditText name = findViewById(R.id.name);
    try {
      fos = openFileOutput("hello.txt", MODE_APPEND); // Use MODE_APPEND to append data
      fos.write(name.getText().toString().getBytes());
      name.getText().clear();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      assert fos != null;
      Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
      fos.close();
    }
  }

  public void onViewFile(View view) throws IOException {
    FileInputStream fis = null;
    try {
      fis = openFileInput("hello.txt");
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      StringBuilder sb = new StringBuilder();
      String text;
      while ((text = br.readLine()) != null) {
        sb.append(text).append("\n");
      }
      TextView name = findViewById(R.id.name);
      name.setText(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      assert fis != null;
      fis.close();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getSharedPreferences("com.example.slide9", Context.MODE_PRIVATE).getBoolean("isLoggedIn", false)) {
      startActivity(new Intent(this, MainActivity2.class));
      return;
    }
    setContentView(R.layout.activity_main);
  }
}