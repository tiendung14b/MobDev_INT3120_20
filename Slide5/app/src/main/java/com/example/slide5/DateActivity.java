package com.example.slide5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    TextView lblDateAndTime;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE , minute);
        updateLabel();
    };

    private void updateLabel() {
        lblDateAndTime.setText(myCalendar.getTime().toString());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        lblDateAndTime = (TextView) findViewById(R.id.lblDateAndTime);
        Button btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(v -> new DatePickerDialog(this, d,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        Button btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(v -> new TimePickerDialog(this, t, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show());
        updateLabel();
    }
}