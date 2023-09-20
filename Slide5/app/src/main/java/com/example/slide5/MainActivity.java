package com.example.slide5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDate(View view) {
        startActivity(new Intent(getApplicationContext(), DateActivity.class));
    }

    public void onTab(View view) {
        startActivity(new Intent(getApplicationContext(), Tab.class));
    }
}