package com.example.raghadtaleb.project8_inventoryapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raghadtaleb on 27/01/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();


    private static final String DATABASE_NAME = "nachos.db";
    private static final int DATABASE_VERSION = 1;


    String SQL_CREATE_NACHOS_TABLE = "CREATE TABLE " + Contract.nachosEntry.TABLE_NAME + " ("
            + Contract.nachosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Contract.nachosEntry.COLUMN_NACHOS_NAME + " TEXT NOT NULL, "
            + Contract.nachosEntry.COLUMN_PRICE + " INTEGER NOT NULL, "
            + Contract.nachosEntry.COLUMN_QUANTITY + " INTEGER, "
            + Contract.nachosEntry.COLUMN_SUPPLIER + " TEXT, "
            +Contract.nachosEntry.COLUMN_SUPER_PHONE + " INTEGER, "
            + Contract.nachosEntry.COLUMN_SUPP_EMAIL + " TEXT);";





    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_NACHOS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
