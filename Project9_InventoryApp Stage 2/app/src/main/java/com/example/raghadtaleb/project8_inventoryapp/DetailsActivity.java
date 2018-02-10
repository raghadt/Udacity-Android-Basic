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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raghadtaleb.project8_inventoryapp.data.Contract.nachosEntry;

/**
 * Created by raghadtaleb on 06/02/2018.
 */

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_ITEM_LOADER = 0;

    private Uri currentItemUri;

    private TextView itemTextView;
    private TextView suppTextView;
    private TextView quantityTextView;
    private TextView itemPriceText;
    private TextView suppPhoneText;
    private TextView suppEmailText;


    private Button call;
    private Button mail;
    private Button increase;
    private Button decrease;


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
        setContentView(R.layout.activity_details);


        Intent intent = getIntent();
        currentItemUri = intent.getData();

        if (currentItemUri != null) {
            setTitle(R.string.editing_item);
            getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);

        } else {
            setTitle(R.string.add_new_item);
            invalidateOptionsMenu();
        }

        itemTextView = findViewById(R.id.edit_itemname);
        suppTextView = findViewById(R.id.edit_suppname);
        itemPriceText = findViewById(R.id.edit_price);
        quantityTextView = findViewById(R.id.edit_quant);
        suppPhoneText = findViewById(R.id.edit_phone);
        suppEmailText = findViewById(R.id.edit_mail);

        call = findViewById(R.id.call);
        mail = findViewById(R.id.email);
        increase = findViewById(R.id.increase_button);
        decrease = findViewById(R.id.decrease_button);


        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{suppEmailText.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Nachos Shop Request");

                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailsActivity.this, "No email for client", Toast.LENGTH_SHORT).show();
                }

            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + suppPhoneText.getText().toString()));
                startActivity(intent);
            }
        });

    }

    private void increment() {

        String quantityString = quantityTextView.getText().toString().trim();


        int quant = Integer.parseInt(quantityString);
        quant = quant + 1;

        ContentValues values = new ContentValues();
        values.put(nachosEntry.COLUMN_QUANTITY, quant);

        int rowsAffected = getContentResolver().update(currentItemUri, values,
                null, null);
    }


    private void decrement() {
        String quantityString = quantityTextView.getText().toString().trim();


        int quant = Integer.parseInt(quantityString);

        if (quant > 0) {
            quant = quant - 1;
        } else if (quant == 0) {
            Toast.makeText(DetailsActivity.this, getString(R.string.zero_error),
                    Toast.LENGTH_SHORT).show();
        }

        ContentValues values = new ContentValues();

        values.put(nachosEntry.COLUMN_QUANTITY, quant);


        int rowsAffected = getContentResolver().update(currentItemUri, values,
                null, null);
    }


    //----------------------- Other methods ---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(DetailsActivity.this, EditorsActivity.class);
                intent.setData(currentItemUri);
                startActivityForResult(intent, 9);
                return true;


            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;


            case android.R.id.home:
                if (!changedItem) {
                    NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                    return true;
                }


                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(DetailsActivity.this);
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
                nachosEntry.COLUMN_SUPER_PHONE,
                nachosEntry.COLUMN_SUPP_EMAIL,

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
            int phoneIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);
            int mailIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);

            String name = cursor.getString(nameIndex);
            String supplier = cursor.getString(supplierIndex);
            int quant = cursor.getInt(quantityIndex);
            int price = cursor.getInt(priceIndex);
            int phone = cursor.getInt(phoneIndex);
            String email = cursor.getString(mailIndex);

            itemTextView.setText(name);
            suppTextView.setText(supplier);
            quantityTextView.setText(Integer.toString(quant));
            itemPriceText.setText(Integer.toString(price));
            suppPhoneText.setText(Integer.toString(phone));
            suppEmailText.setText(email);

        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemTextView.setText("");
        suppTextView.setText("");
        quantityTextView.setText("");
        itemPriceText.setText("");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
