package com.example.slide7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {
    TextView message;
    String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        message = findViewById(R.id.mes);
        Intent intent = this.getIntent();
        fullname = intent.getStringExtra("fullname");
        message.setText(intent.getStringExtra("message"));
    }

    public void onBack(View view) {
        this.onBackPressed();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        String feedback = "Helloo " + fullname + ", this is a response";
        data.putExtra("feedback", feedback);

        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}