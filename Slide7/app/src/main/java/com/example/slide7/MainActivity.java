package com.example.slide7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

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

    public void onPhone(View view) {
        EditText phoneInput = findViewById(R.id.phoneInput);
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneInput.getText().toString())));
    }

    public void onBruh(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Em ơi không cần tình yêu đâu, chỉ cần em đam mê = ))))");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setComponent(new ComponentName("com.example.slide7", "com.example.slide7.Bruh"));
        startActivity(intent);
    }

    public void onNavMessage(View view) {
        EditText mesInput = findViewById(R.id.messageInput);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:5551234"));
        intent.putExtra("sms_body", mesInput.getText().toString());
        startActivity(intent);
    }

    public void onImageView(View view) {
        Intent myIntent = new Intent();
        myIntent.setType("image/pictures/*");
        myIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(myIntent);
    }

    public void onEditPhone(View view) {
        String myData = "content://contacts/people/8";
        Intent myActivity2 = new Intent(Intent.ACTION_EDIT, Uri.parse(myData));
        startActivity(myActivity2);
    }

    public void onSeeMap(View view) {
        String geoCode = "geo:0,0?q=1860+east+18th+street+cleveland+oh";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoCode));
        startActivity(intent);
    }

    public void onOpenPlayer(View view) {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        startActivity(intent);
    }
}