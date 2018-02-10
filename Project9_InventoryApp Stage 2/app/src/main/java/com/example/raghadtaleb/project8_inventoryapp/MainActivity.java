package com.example.raghadtaleb.project8_inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
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
import android.widget.Button;
import android.widget.ListView;

import com.example.raghadtaleb.project8_inventoryapp.data.Contract;
import com.example.raghadtaleb.project8_inventoryapp.data.Contract.nachosEntry;
import com.example.raghadtaleb.project8_inventoryapp.data.DbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    final static EditorsActivity editorsActivity = new EditorsActivity();
    private static final int ITEM_LOADER = 0;
    DbHelper helper;
    ItemsCursorActivity cursorAdapter;
    private Button sell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        helper = new DbHelper(this);


        FloatingActionButton floatin = findViewById(R.id.floatin);
        floatin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        ListView itemlistView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        itemlistView.setEmptyView(emptyView);

        cursorAdapter = new ItemsCursorActivity(this, null);
        itemlistView.setAdapter(cursorAdapter);


        itemlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                Uri currentItemUri = ContentUris.withAppendedId(nachosEntry.CONTENT_URI, id);
                intent.setData(currentItemUri);
                startActivity(intent);

            }
        });

        getLoaderManager().initLoader(ITEM_LOADER, null, this);
    }


    private void deleteAllItems() {
        int deletedRows = getContentResolver().delete(nachosEntry.CONTENT_URI, null, null);
        Log.v("MainActivity", deletedRows + " ---------- deleted rows ");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        String[] colum = {
                nachosEntry._ID,
                nachosEntry.COLUMN_NACHOS_NAME,
                nachosEntry.COLUMN_SUPPLIER,
                nachosEntry.COLUMN_QUANTITY,
                nachosEntry.COLUMN_PRICE,
        };

        return new CursorLoader(this, nachosEntry.CONTENT_URI, colum, null, null, null);
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
