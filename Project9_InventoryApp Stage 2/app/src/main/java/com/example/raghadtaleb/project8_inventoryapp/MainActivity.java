package com.example.raghadtaleb.project8_inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.raghadtaleb.project8_inventoryapp.data.Contract;
import com.example.raghadtaleb.project8_inventoryapp.data.DbHelper;
import com.example.raghadtaleb.project8_inventoryapp.data.Contract.nachosEntry;

public class MainActivity extends  AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    DbHelper helper;
    ItemsCursorActivity cursorAdapter;
    private static final int ITEM_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        helper = new DbHelper(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, EditorsActivity.class);
                startActivity(intent);
            }
        });


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

    private void deleteAllItems(){
        int rowsDeleted = getContentResolver().delete(nachosEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + "rows deleted from cricket database");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete_all_data:
                deleteAllItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private Cursor readDb() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] project = {Contract.nachosEntry._ID, Contract.nachosEntry.COLUMN_NACHOS_NAME, Contract.nachosEntry.COLUMN_QUANTITY};
        return database.query(Contract.nachosEntry.TABLE_NAME, project, null, null, null, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] colum = {
                nachosEntry._ID,
                nachosEntry.COLUMN_NACHOS_NAME,
                nachosEntry.COLUMN_SUPPLIER,
                nachosEntry.COLUMN_QUANTITY,
                nachosEntry.COLUMN_PRICE,
        };

        return new CursorLoader(this, nachosEntry.CONTENT_URI, colum, null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);

    }
}