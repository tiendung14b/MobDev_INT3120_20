package com.example.slide4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.contextmenu);
        this.registerForContextMenu(button);

    }

    public void onSpinner(View view) {
        startActivity(new Intent(getApplicationContext(), Spinner.class));
    }

    public void onGrid(View view) {
        startActivity(new Intent(getApplicationContext(), GridView.class));
    }

    public void onAuto(View view) {
        startActivity(new Intent(getApplicationContext(), CompleteTextViw.class));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.spinner) {
            setContentView(R.layout.activity_spinner);
        }
        return super.onOptionsItemSelected(item);
    }
}