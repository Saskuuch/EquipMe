package com.example.equipme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

//Unused class
public final class DatabaseContract {

    private DatabaseContract(){}

    public static class DataEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME = "item";
    }

    private static final String CREATE_ENTRIES = "CREATE TABLE " + DataEntry.TABLE_NAME + " (" +
            DataEntry._ID + " INTEGER PRIMARY KEY, " + DataEntry.COLUMN_NAME + " TEXT)";

    private static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;

    public class DataHelper extends SQLiteOpenHelper{
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "EquipmentReader.db";

        public DataHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int old, int newversion) {
            db.execSQL(DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
