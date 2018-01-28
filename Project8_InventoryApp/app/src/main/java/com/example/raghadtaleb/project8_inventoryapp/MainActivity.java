package com.example.raghadtaleb.project8_inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DbHelper(this);
//        insert();
    }

    public void insertNachos(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.nachosEntry.COLUMN_NACHOS_NAME, "Cheesy Nachos");
        values.put(Contract.nachosEntry.COLUMN_PRICE, 10);
        values.put(Contract.nachosEntry.COLUMN_QUANTITY, 100);
        values.put(Contract.nachosEntry.COLUMN_SUPPLIER, "Dee");
        values.put(Contract.nachosEntry.COLUMN_SUPER_PHONE, 053331);
        values.put(Contract.nachosEntry.COLUMN_SUPP_EMAIL, "nachos@info.com");


        db.insert(Contract.nachosEntry.TABLE_NAME, null, values);
        Cursor cursor = db.query(Contract.nachosEntry.TABLE_NAME, null, null, null, null, null, null);


        TextView scoreView = (TextView) findViewById(R.id.txt);
        scoreView.setText("Items in the Database " + cursor.getCount());

        readDb();


        cursor.close();
    }

    private Cursor readDb() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] project = {Contract.nachosEntry._ID, Contract.nachosEntry.COLUMN_NACHOS_NAME, Contract.nachosEntry.COLUMN_QUANTITY};
        return database.query(Contract.nachosEntry.TABLE_NAME, project, null, null, null, null, null);
    }
}