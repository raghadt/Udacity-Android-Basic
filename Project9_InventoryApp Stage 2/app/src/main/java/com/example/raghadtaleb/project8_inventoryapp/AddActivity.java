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
 * Created by raghadtaleb on 10/02/2018.
 */

public class AddActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_ITEM_LOADER = 0;

    private Uri currentItemUri;

    private EditText itemEditText;
    private EditText suppEditText;
    private EditText quantityEditText;
    private EditText itemPriceText;
    private EditText suppPhone;
    private EditText suppEmail;


    private Button quantityIncrement;
    private Button quantityDecrement;
    private Button priceIncrement;
    private Button priceDecrement;

    private boolean changedItem = false;


    private View.OnTouchListener touchListener = new View.OnTouchListener() {

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

        if (currentItemUri != null) {
            setTitle(R.string.editing_item);
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);

        } else {
            setTitle(R.string.add_new_item);
            invalidateOptionsMenu();
        }

        itemEditText = findViewById(R.id.edit_itemname);
        suppEditText = findViewById(R.id.edit_suppname);
        itemPriceText = findViewById(R.id.edit_price);
        quantityEditText = findViewById(R.id.edit_quant);
        quantityIncrement = findViewById(R.id.incQuant);
        quantityDecrement = findViewById(R.id.decQuant);
        priceIncrement = findViewById(R.id.incPRICE);
        priceDecrement = findViewById(R.id.decPRICE);
        suppPhone = findViewById(R.id.edit_phone);
        suppEmail = findViewById(R.id.edit_mail);


        itemEditText.setOnTouchListener(touchListener);
        suppEditText.setOnTouchListener(touchListener);
        itemPriceText.setOnTouchListener(touchListener);
        quantityEditText.setOnTouchListener(touchListener);


        quantityIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incAmount();
                changedItem = true;
            }
        });

        quantityDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decAmount();
                changedItem = true;
            }
        });

        priceIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incPrice();
                changedItem = true;
            }
        });

        priceDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decPrice();
                changedItem = true;
            }
        });

    }

    //----------------------- Decrement Amount ---------------------


    public void decAmount() {
        String prevValueString = quantityEditText.getText().toString();

        if (prevValueString.isEmpty()) {
            return;
        } else if (prevValueString.equals("0")) {
            return;
        } else {
            int previousValue = Integer.parseInt(prevValueString);
            quantityEditText.setText(String.valueOf(previousValue - 1));
        }
    }

    //----------------------- Increment Amount ---------------------

    private void incAmount() {
        String prevValueString = quantityEditText.getText().toString();
        int previousValue = 0;

        if (prevValueString.isEmpty()) {
            quantityEditText.setText(String.valueOf(previousValue + 1));

        } else {
            previousValue = Integer.parseInt(prevValueString);
            quantityEditText.setText(String.valueOf(previousValue + 1));
        }
    }

    //----------------------- Decrement Price ---------------------

    private void decPrice() {
        String preValueString = itemPriceText.getText().toString();

        if (preValueString.isEmpty()) {
            return;
        } else if (preValueString.equals("0") || preValueString.contains("-")) {
            return;
        } else {
            int previousValue = Integer.parseInt(preValueString);
            itemPriceText.setText(String.valueOf(previousValue - 1));
        }
    }

    //----------------------- Increment Price ---------------------

    private void incPrice() {
        String preValueString = itemPriceText.getText().toString();
        int previousValue = 0;

        if (preValueString.isEmpty()) {
            itemPriceText.setText(String.valueOf(previousValue + 1));

        } else {
            previousValue = Integer.parseInt(preValueString);
            itemPriceText.setText(String.valueOf(previousValue + 1));
        }
    }


    //----------------------- Save Item ---------------------

    private void saveItem() {

        int price = 0;
        int quantity = 0;
        int changedRows;
        int phone = 0;
        String email = "";

        String itemString = itemEditText.getText().toString().trim();
        String suppString = suppEditText.getText().toString().trim();
        String priceString = itemPriceText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();
        String suppPhoneString = suppPhone.getText().toString().trim();
        String suppEmailString = suppEmail.getText().toString().trim();


        if (currentItemUri == null &&
                TextUtils.isEmpty(itemString) && TextUtils.isEmpty(suppString)
                ) {

            Toast.makeText(this, getString(R.string.correct_info),
                    Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(itemString)) {
            Toast.makeText(this, getString(R.string.correct_info),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(suppEmailString)) {
            Toast.makeText(this, getString(R.string.correct_info),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(priceString)) {
            price = Integer.parseInt(priceString);
        } else {
            Toast.makeText(this, getString(R.string.correct_info),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        } else {
            Toast.makeText(this, getString(R.string.correct_info),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(suppPhoneString)) {
            phone = Integer.parseInt(suppPhoneString);
        } else {
            Toast.makeText(this, getString(R.string.correct_info),
                    Toast.LENGTH_SHORT).show();
            return;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(nachosEntry.COLUMN_NACHOS_NAME, itemString);
        contentValues.put(nachosEntry.COLUMN_SUPPLIER, suppString);
        contentValues.put(nachosEntry.COLUMN_PRICE, priceString);
        contentValues.put(nachosEntry.COLUMN_QUANTITY, quantityString);
        contentValues.put(nachosEntry.COLUMN_SUPP_EMAIL, suppEmailString);


        contentValues.put(nachosEntry.COLUMN_PRICE, price);
        contentValues.put(nachosEntry.COLUMN_QUANTITY, quantity);
        contentValues.put(nachosEntry.COLUMN_SUPER_PHONE, phone);


        if (currentItemUri == null) {

            Uri newUri = getContentResolver().insert(nachosEntry.CONTENT_URI, contentValues);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.insert_item_failed),
                        Toast.LENGTH_SHORT).show();
                Log.d("EditorsActivity", "Failed to insert item");
            } else {
                Toast.makeText(this, getString(R.string.insert_item_succ),
                        Toast.LENGTH_SHORT).show();
            }

        } else {

            changedRows = getContentResolver().update(currentItemUri, contentValues, null, null);
            if (changedRows == 0) {
                Toast.makeText(this, getString(R.string.insert_item_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_item_succ),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    //----------------------- Other methods ---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                saveItem();
                finish();
                return true;


            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;


            case android.R.id.home:
                if (!changedItem) {
                    NavUtils.navigateUpFromSameTask(AddActivity.this);
                    return true;
                }


                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(AddActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit_no_save);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.no_exit, new DialogInterface.OnClickListener() {
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


    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_item);
        builder.setPositiveButton(R.string.delete_butt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel_butt, new DialogInterface.OnClickListener() {
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

    private void deleteItem() {
        int deletedRows;
        if (currentItemUri != null) {
            deletedRows = getContentResolver().delete(currentItemUri, null, null);

            if (deletedRows == 0) {
                Toast.makeText(this, getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_succ),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!changedItem) {
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
        String[] projection = {
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
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(nachosEntry.COLUMN_NACHOS_NAME);
            int supplierIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPPLIER);
            int quantityIndex = cursor.getColumnIndex(nachosEntry.COLUMN_QUANTITY);
            int priceIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);
            int suppPhoneNumberIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPER_PHONE);
            int suppEmailIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPP_EMAIL);

            String name = cursor.getString(nameIndex);
            String supplier = cursor.getString(supplierIndex);
            int quant = cursor.getInt(quantityIndex);
            int price = cursor.getInt(priceIndex);
            int phoneNumber = cursor.getInt(suppPhoneNumberIndex);
            String email = cursor.getString(suppEmailIndex);

            itemEditText.setText(name);
            suppEditText.setText(supplier);
            quantityEditText.setText(Integer.toString(quant));
            itemPriceText.setText(Integer.toString(price));
            suppPhone.setText(Integer.toString(phoneNumber));
            suppEmail.setText(email);
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemEditText.setText("");
        suppEditText.setText("");
        quantityEditText.setText("");
        itemPriceText.setText("");
        suppPhone.setText("");
        suppEmail.setText("");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
