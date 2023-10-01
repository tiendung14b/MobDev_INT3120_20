package com.example.slide10;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import java.util.Arrays;

public class UserProvider extends ContentProvider {
  UserModel userModel;
  SQLiteDatabase db;
  public UserProvider() {
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    db.delete("user", selection, selectionArgs);
    return 0;
  }

  @Override
  public String getType(Uri uri) {
    // TODO: Implement this to handle requests for the MIME type of the data
    // at the given URI.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    Toast.makeText(getContext(), "User added", Toast.LENGTH_SHORT).show();
    db.insert("user", null, values);
    return null;
  }

  @Override
  public boolean onCreate() {
    userModel = new UserModel(getContext());
    db = userModel.getWritableDatabase();
    return false;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    return db.query("user", projection, selection, selectionArgs, null, null, sortOrder);
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    db.update("user", values, selection, selectionArgs);
    return 0;
  }
}