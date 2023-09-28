package com.example.slide9;

import static com.example.slide9.FeedReaderContract.FeedEntry.DATABASE_NAME;
import static com.example.slide9.FeedReaderContract.FeedEntry.DATABASE_VERSION;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
  private static final String SQL_CREATE_ENTRIES =
          "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
  FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +

  FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +

  FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

  public FeedReaderDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_ENTRIES);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }

}
