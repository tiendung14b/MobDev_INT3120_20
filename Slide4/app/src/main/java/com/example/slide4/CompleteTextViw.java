package com.example.slide4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class CompleteTextViw extends AppCompatActivity implements TextWatcher {
    TextView selection;
    AutoCompleteTextView edit;
    String[] items = {"Android","IPhone","WindowsMobile", "Blackberry","WebOS","Ubuntu","Windows7", "Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_text_viw);
        selection = (TextView) findViewById(R.id.selection);
        edit = (AutoCompleteTextView) findViewById(R.id.edit);
        edit.addTextChangedListener(this);
        edit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items));


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        selection.setText(edit.getText());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}