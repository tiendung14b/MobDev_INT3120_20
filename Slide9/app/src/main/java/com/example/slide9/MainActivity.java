package com.example.slide9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
  FeedReaderDbHelper mDbHelper;

  public void onLogin(View view) {
    SharedPreferences sharedPreferences = getSharedPreferences("com.example.slide9", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("isLoggedIn", true);
    editor.apply();
    startActivity(new Intent(this, MainActivity2.class));
  }

  public void onEditFile(View view) throws IOException {
    FileOutputStream fos = null;
    EditText name = findViewById(R.id.nameinput);
    try { // Use MODE_APPEND to append data
      fos = openFileOutput("hello.txt", Context.MODE_PRIVATE);
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
        sb.append(text);
      }
      TextView name = findViewById(R.id.name);
      name.setText(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fis != null) {
        fis.close();
      }
    }
  }

  public void onSaveExtFile(View view) {
    File dir = new File(Environment.getExternalStorageDirectory(),"MyFile");
    if (!dir.exists()){
      dir.mkdir();
    }
    EditText nameinput = findViewById(R.id.nameinput);
    File file = new File(dir,nameinput.getText().toString());
    OutputStream outputStream = null;
    String txt = nameinput.getText().toString();
    try {
      outputStream = new FileOutputStream(file);
      outputStream.write(txt.getBytes());
      Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_SHORT).show();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
    }
  }

  public void onReadFile(View v) throws IOException {
    boolean isExternalStorageReadable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
            || Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    if (!isExternalStorageReadable) {
      Toast.makeText(this, "External storage is not readable", Toast.LENGTH_SHORT).show();
      return;
    }
    StringBuilder sb = new StringBuilder();
    File dir = new File(Environment.getExternalStorageDirectory(),"MyFile");
    File textFile = new File(dir,"hell.txt");
    FileInputStream fis = new FileInputStream(textFile);
    if (fis != null) {
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      TextView txt = findViewById(R.id.txt);
      txt.setText(sb.toString());
      fis.close();
    }
  }

  public void onViewAll(View view) {
    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
    StringBuffer buffer = new StringBuffer();
    while (cursor.moveToNext()) {
      buffer.append("ID: " + cursor.getString(0) + "\n");
      buffer.append("Title: " + cursor.getString(1) + "\n");
      buffer.append("Subtitle: " + cursor.getString(2) + "\n\n");
    }
    TextView txt = findViewById(R.id.txt);
    txt.setText(buffer.toString());
  }

  public void onInsert(View view) {
    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    String title = "bruhh";
    String subtitle = "bro";
    String sql = "INSERT INTO " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" + FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + ", " + FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + ") VALUES ('" + title + "', '" + subtitle + "')";
    db.execSQL(sql);
    Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mDbHelper = new FeedReaderDbHelper(this);
    if (getSharedPreferences("com.example.slide9", Context.MODE_PRIVATE).getBoolean("isLoggedIn", false)) {
      startActivity(new Intent(this, MainActivity2.class));
      return;
    }
    setContentView(R.layout.activity_main);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
      }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      if (!Environment.isExternalStorageManager()) {
        try {
          Intent intent = new Intent();
          intent.addCategory("android.intent.category.DEFAULT");
          intent.setAction(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
          intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
          startActivityIfNeeded(intent, 101);
        } catch (Exception e) {
          Intent intent = new Intent();
          intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
          startActivityIfNeeded(intent, 101);
        }
      }
    }
  }
}