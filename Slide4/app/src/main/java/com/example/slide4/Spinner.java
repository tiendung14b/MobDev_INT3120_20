package com.example.slide4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Spinner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        uper.onCreate(icicle);
        setContentView(R.layout.activity_main);
        TextView selection = findViewById(R.id.selection);
        android.widget.Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
        this, android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

    }

    public void onItemSelected(
                               AdapterView<?> parent, View v, int position, long id) {
        selection.setText(items[position]);
    }
    public void onNothingSelected(AdapterView<?> parent) {
        selection.setText("");
    }
    public void onItemSelected(
                               AdapterView<?> parent, View v, int position, long id) {
        selection.setText(items[position]);
    }
    public void onNothingSelected(AdapterView<?> parent) {
        selection.setText("");
    }

}