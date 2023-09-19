package com.example.slide6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.registerForContextMenu(findViewById(R.id.button));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("You touched me!");
        getMenuInflater().inflate(R.menu.option_menu, menu);
    }

    public void onPopUpMenuClick(View view) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), findViewById(R.id.button2));
        popupMenu.inflate(R.menu.option_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> true);
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.otp1) {
            Toast.makeText(getApplicationContext(), "otp1", Toast.LENGTH_SHORT).show();
        } else {
            //dosomething
        }
        return super.onOptionsItemSelected(item);
    }
}