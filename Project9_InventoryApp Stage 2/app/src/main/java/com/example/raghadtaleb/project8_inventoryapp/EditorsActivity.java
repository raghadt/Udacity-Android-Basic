package com.example.raghadtaleb.project8_inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.raghadtaleb.project8_inventoryapp.data.Contract.nachosEntry;

/**
 * Created by raghadtaleb on 02/02/2018.
 */

public class EditorsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_ITEM_LOADER = 0;

    private Uri currentItemUri;
    private EditText itemEditText;
    private EditText brandEditText;
    private EditText quantityEditText;
    private EditText itemPriceText;
    private Button quantityIncrement;
    private Button quantityDecrement;
    private Button priceIncrement;
    private Button priceDecrement;

    private boolean changedItem = false;


    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            changedItem = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        currentItemUri = intent.getData();

        if(currentItemUri!=null){
            setTitle(R.string.editor_activity_title_edit_an_item);
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
        } else{
            setTitle(R.string.editor_activity_title_new_item);
            invalidateOptionsMenu();
        }

        itemEditText =  findViewById(R.id.edit_item_name);
        brandEditText =   findViewById(R.id.edit_item_brand);
        itemPriceText =   findViewById(R.id.edit_item_price);
        quantityEditText =   findViewById(R.id.edit_item_quantity);
        quantityIncrement =   findViewById(R.id.quantity_increment);
        quantityDecrement =  findViewById(R.id.quantity_decrement);
        priceIncrement = findViewById(R.id.price_increment);
        priceDecrement = findViewById(R.id.price_decrement);


        itemEditText.setOnTouchListener(mTouchListener);
        brandEditText.setOnTouchListener(mTouchListener);
        itemPriceText.setOnTouchListener(mTouchListener);
        quantityEditText.setOnTouchListener(mTouchListener);


        quantityIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityIncrement();
                changedItem = true;
            }
        });

        quantityDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityDecrement();
                changedItem = true;
            }
        });

        priceIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceIncrement();
                changedItem = true;
            }
        });

        priceDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceDecrement();
                changedItem = true;
            }
        });

    }

    //--------------------------------------------


    private void quantityDecrement() {
        String previousValueString = quantityEditText.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            return;
        } else if (previousValueString.equals("0")) {
            return;
        } else {
            previousValue = Integer.parseInt(previousValueString);
            quantityEditText.setText(String.valueOf(previousValue - 1));
        }
    }

    private void quantityIncrement() {
        String previousValueString = quantityEditText.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            previousValue = 0;
        } else {
            previousValue = Integer.parseInt(previousValueString);
        }
        quantityEditText.setText(String.valueOf(previousValue + 1));
    }

    private void priceDecrement() {
        String previousValueString = itemPriceText.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            return;
        } else if (previousValueString.equals("0")) {
            return;
        } else {
            previousValue = Integer.parseInt(previousValueString);
            itemPriceText.setText(String.valueOf(previousValue - 1));
        }
    }

    private void priceIncrement() {
        String previousValueString = itemPriceText.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            previousValue = 0;
        } else {
            previousValue = Integer.parseInt(previousValueString);
        }
        itemPriceText.setText(String.valueOf(previousValue + 1));
    }



    private void saveItem () {


        String itemString = itemEditText.getText().toString().trim();
        String brandString = brandEditText.getText().toString().trim();
        String priceString = itemPriceText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();


        if (currentItemUri == null &&
                TextUtils.isEmpty(itemString) && TextUtils.isEmpty(brandString) && TextUtils.isEmpty(priceString)
                && TextUtils.isEmpty(quantityString)){

            return;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(nachosEntry.COLUMN_NACHOS_NAME, itemString);
        contentValues.put(nachosEntry.COLUMN_SUPPLIER, brandString);
        contentValues.put(nachosEntry.COLUMN_PRICE, priceString);
        contentValues.put(nachosEntry.COLUMN_QUANTITY, quantityString);


        int price = 0;
        if(!TextUtils.isEmpty(priceString)){
            price = Integer.parseInt(priceString);
        }

        contentValues.put(nachosEntry.COLUMN_PRICE, price);

        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)){
            quantity = Integer.parseInt(quantityString);
        }

        contentValues.put(nachosEntry.COLUMN_QUANTITY, quantity);

    if (currentItemUri == null){

            Uri newUri = getContentResolver().insert(nachosEntry.CONTENT_URI, contentValues);

            if (newUri == null) {

                Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                        Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, getString(R.string.editor_insert_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {


            int rowsAffected = getContentResolver().update(currentItemUri, contentValues, null, null);



            if (rowsAffected == 0){
                Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                        Toast.LENGTH_SHORT).show();

            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){


            case R.id.action_save:


                saveItem();


                finish();
                return true;


            case R.id.action_delete:


                showDeleteConfirmationDialog();
                return true;


            case android.R.id.home:


                if (!changedItem) {
                    NavUtils.navigateUpFromSameTask(EditorsActivity.this);
                    return true;
                }



                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                NavUtils.navigateUpFromSameTask(EditorsActivity.this);
                            }
                        };


                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog () {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (dialog != null) {
                    dialog.dismiss();
                }

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteItem () {


        if (currentItemUri != null) {


            int rowsDeleted = getContentResolver().delete(currentItemUri, null, null);



            if (rowsDeleted == 0) {


                Toast.makeText(this, getString(R.string.editor_delete_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {


                Toast.makeText(this, getString(R.string.editor_delete_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }


        finish();
    }

    @Override
    public void onBackPressed() {
        if(!changedItem){
            super.onBackPressed();
            return;
        }



        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {


                finish();
            }
        };


        showUnsavedChangesDialog(discardButtonClickListener);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projection = {
                nachosEntry._ID,
                nachosEntry.COLUMN_NACHOS_NAME,
                nachosEntry.COLUMN_SUPPLIER,
                nachosEntry.COLUMN_QUANTITY,
                nachosEntry.COLUMN_PRICE,

        };

        return new CursorLoader(this,
                currentItemUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor.moveToFirst()){

            int nameColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_NACHOS_NAME);
            int brandColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPPLIER);
            int quantityColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);


            String name = cursor.getString(nameColumnIndex);
            String brand = cursor.getString(brandColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            int price = cursor.getInt(priceColumnIndex);


            itemEditText.setText(name);
            brandEditText.setText(brand);
            itemPriceText.setText(Integer.toString(price));
            quantityEditText.setText(Integer.toString(quantity));
        }
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemEditText.setText("");
        brandEditText.setText("");
        quantityEditText.setText("");
        itemPriceText.setText("");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
