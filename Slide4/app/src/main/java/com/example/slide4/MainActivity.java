package com.example.slide4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onSpinner(View view) {
        startActivity(new Intent(getApplicationContext(), Spinner.class));
    }

    public void onGrid(View view) {
        startActivity(new Intent(getApplicationContext(), GridView.class));
    }

    public void onAuto(View view) {
        startActivity(new Intent(getApplicationContext(), CompleteTextViw.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}