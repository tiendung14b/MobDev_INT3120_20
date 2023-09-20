package com.example.slide4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GridView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView selection;
    String[] items = {"Android","IPhone","WindowsMobile", "Blackberry","WebOS","Ubuntu","Windows7", "Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        selection = (TextView) findViewById(R.id.selection);
        android.widget.GridView gv = findViewById(R.id.grid);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        gv.setAdapter(aa);
        gv.setOnItemClickListener(this::onItemSelected);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selection.setText(items[i]);
    }

    public void onBackO(View view) {
        onBackPressed();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}