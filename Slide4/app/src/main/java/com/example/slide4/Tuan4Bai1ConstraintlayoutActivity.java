package com.example.slide4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

public class Tuan4Bai1ConstraintlayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuan4_bai1_constraintlayout);

        NumberPicker numberPicker1 = findViewById(R.id.numberPicker1);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(1000);
        numberPicker1.setOnValueChangedListener((numberPicker, i, i1) -> {

        });
    }
}