package com.example.slide7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int RQC = 1;
    EditText name;
    TextView feedbackBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.input);
        feedbackBox = findViewById(R.id.feedback);
    }

    public void onSendMessage(View view){
        String fullname = name.getText().toString();
        String message = "Hello " + fullname;

        Intent intent = new Intent(this, AnotherActivity.class);
        intent.putExtra("fullname", fullname);
        intent.putExtra("message", message);

        startActivityForResult(intent, RQC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == RQC){
            String feedback = data.getStringExtra("feedback");
            feedbackBox.setText(feedback);
        } else {
            feedbackBox.setText("???");
        }
    }
}