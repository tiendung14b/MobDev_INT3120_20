package com.example.slide4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ListView;
import android.view.View;
import android.widget.ArrayAdapter;

public class ListDisplay extends AppCompatActivity {
    String[] mobileArray = {"Android","IPhone","WindowsMobile",

            "Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        android.widget.ListView listView = findViewById(R.id.mobile_list);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.activity_list_view, mobileArray));
    }

}