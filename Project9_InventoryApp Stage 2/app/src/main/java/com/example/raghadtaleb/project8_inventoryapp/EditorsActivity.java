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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        itemEditText = (EditText) findViewById(R.id.edit_item_name);
        brandEditText = (EditText) findViewById(R.id.edit_item_brand);
        itemPriceText = (EditText) findViewById(R.id.edit_item_price);
        quantityEditText = (EditText) findViewById(R.id.edit_item_quantity);
        quantityIncrement = (Button) findViewById(R.id.quantity_increment);
        quantityDecrement = (Button) findViewById(R.id.quantity_decrement);
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

        //Get information from the seperate fields
        String itemString = itemEditText.getText().toString().trim();
        String brandString = brandEditText.getText().toString().trim();
        String priceString = itemPriceText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();


        //check if this is supposed to be a new item
        //Check if all the fields in the editor are blank
        if (currentItemUri == null &&
                TextUtils.isEmpty(itemString) && TextUtils.isEmpty(brandString) && TextUtils.isEmpty(priceString)
                && TextUtils.isEmpty(quantityString)){

            /**
             *Since no fields were modified, we can return early without creating a new item
             * No need to create contentValues and no need to do any ContentProvider Operations.
             */
            return;
        }

        //Add values to ContentValues object
        ContentValues values = new ContentValues();
        values.put(nachosEntry.COLUMN_NACHOS_NAME, itemString);
        values.put(nachosEntry.COLUMN_SUPPLIER, brandString);
        values.put(nachosEntry.COLUMN_PRICE, priceString);
        values.put(nachosEntry.COLUMN_QUANTITY, quantityString);

        // If the price is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int price = 0;
        if(!TextUtils.isEmpty(priceString)){
            price = Integer.parseInt(priceString);
        }

        values.put(nachosEntry.COLUMN_PRICE, price);

        // If the Quantity is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default

        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)){
            quantity = Integer.parseInt(quantityString);
        }

        values.put(nachosEntry.COLUMN_QUANTITY, quantity);

        // Determine if this is a new or existing item by chekcing if mCurrentItemUri is null or not
        if (currentItemUri == null){
            // This is NEW item, so insert a new item into the provider,
            // returning the content URI for the new item.

            Uri newUri = getContentResolver().insert(nachosEntry.CONTENT_URI, values);

            //Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {

                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_item_failed),
                        Toast.LENGTH_SHORT).show();

            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING item, so update the item with content URI: mCurrentItemUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(currentItemUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0){
                // If no rows were effected, then there was an error with the update.
                // If the new content URI is null, then there was an error with insertion.
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
            //Respond to a click on the "Save" menu option
            case R.id.action_save:
                //add item to the database
                saveItem();
                //finish the activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog to deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the item hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!changedItem) {
                    NavUtils.navigateUpFromSameTask(EditorsActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //User clicked "Discard button", navigate to parent activity
                                NavUtils.navigateUpFromSameTask(EditorsActivity.this);
                            }
                        };
                //Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "keep editing" button, so dismiss the dialog
                // aqnd continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog () {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item.
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }

            }
        });

        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteItem () {
        // Only perform the delete if this is an existing item.
        if (currentItemUri != null) {
            // Call the ContentResolver to delete the item and the given content URI.
            //Pass in null for the selection and selection args because the mCurrentItemUri
            //content URI already identifies the item that we need.
            int rowsDeleted = getContentResolver().delete(currentItemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_item_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }

    @Override
    public void onBackPressed() {
        if(!changedItem){
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should
        // be discarded.
        DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                // User clicked "Discard" button, close the current activity.
                finish();
            }
        };

        //Show dialog that there are unsaved changes
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
            // Find the columns of item attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_NACHOS_NAME);
            int brandColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPPLIER);
            int quantityColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String brand = cursor.getString(brandColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            int price = cursor.getInt(priceColumnIndex);

            // Update the views on the screen with the values form the database
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
