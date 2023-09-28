package com.example.slide9;

import android.provider.BaseColumns;

public final class FeedReaderContract {
  private FeedReaderContract() {}
  public static class FeedEntry implements BaseColumns {

    public static final String DATABASE_NAME = "BookLibrary.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_TITLE = "title";

    public static final String COLUMN_NAME_SUBTITLE = "subtitle";
  }
}
