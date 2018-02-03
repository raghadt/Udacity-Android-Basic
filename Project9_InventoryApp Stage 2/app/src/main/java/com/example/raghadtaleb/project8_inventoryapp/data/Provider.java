package com.example.raghadtaleb.project8_inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.raghadtaleb.project8_inventoryapp.data.Contract.nachosEntry;

/**
 * Created by raghadtaleb on 02/02/2018.
 */

public class Provider extends ContentProvider {


    public static final String LOG_TAG = Provider.class.getSimpleName();


    private static final int ITEMS = 100;

    private  static final int ITEM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH, ITEMS);

        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH + "/#", ITEM_ID);
    }

    private DbHelper dBHelper;


    @Override
    public boolean onCreate() {
        dBHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {


        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Cursor cursor;

        int matcher = sUriMatcher.match(uri);
        //TODO change cursor

        switch(matcher){
            case ITEMS:
                cursor = db.query(nachosEntry.TABLE_NAME, projection, selection, selectionArgs, null,null, sortOrder);
                break;
            case ITEM_ID:
                selection = nachosEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(nachosEntry.TABLE_NAME, projection, selection, selectionArgs, null,null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Querying this URI has failed");

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);


        return cursor;
    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case ITEMS:
                return nachosEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return nachosEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }    }

    //------------------------------ INSERT ------------------------------


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int matcher = sUriMatcher.match(uri);
        switch (matcher){
            case ITEMS:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion failed");

        }
    }

    private Uri insertItem(@NonNull Uri uri, @Nullable ContentValues contentValues){
        String itemName = contentValues.getAsString(nachosEntry.COLUMN_NACHOS_NAME);
        String suppName = contentValues.getAsString(nachosEntry.COLUMN_SUPPLIER);
        String price = contentValues.getAsString(nachosEntry.COLUMN_PRICE);
        String quantity = contentValues.getAsString(nachosEntry.COLUMN_QUANTITY);
        String supPhone = contentValues.getAsString(nachosEntry.COLUMN_SUPER_PHONE);
        String supEmail = contentValues.getAsString(nachosEntry.COLUMN_SUPP_EMAIL);

        if(itemName == null ){
            throw new IllegalArgumentException("You must provide a name for this item");
        } else if(suppName==null){
            throw new IllegalArgumentException("You must provide a supplier name for this item");

        }else if(price == null){
            throw new IllegalArgumentException("You must provide a price for this item");

        }

        SQLiteDatabase db = dBHelper.getWritableDatabase();
        Long id = db.insert(nachosEntry.TABLE_NAME, null, contentValues);

        if (id == -1){
            Log.e(LOG_TAG, "Row insertion failed " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    //------------------------------ DELETE ------------------------------


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dBHelper.getWritableDatabase();
        final int matcher = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (matcher) {
            case ITEMS:
                rowsDeleted = db.delete(nachosEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = nachosEntry._ID + "=?";
                selectionArgs = new String[] { String. valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(nachosEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }
//------------------------------ UPDAET ------------------------------
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int matcher = sUriMatcher.match(uri);

        switch (matcher){
            case ITEMS:
                return updateItem(uri, contentValues, selection, selectionArgs);

            case ITEM_ID:
                selection = nachosEntry._ID + "=?";
                selectionArgs = new String [] { String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can't perform update " + uri);

        }
    }
//---TODO
    private int updateItem(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues.containsKey(nachosEntry.COLUMN_NACHOS_NAME)) {
            String name = contentValues.getAsString(nachosEntry.COLUMN_NACHOS_NAME);
            if (name == null ) {
                throw new IllegalArgumentException("Item requires a description");
            }
        }

        if (contentValues.containsKey(nachosEntry.COLUMN_SUPPLIER)) {
            String brand = contentValues.getAsString(nachosEntry.COLUMN_SUPPLIER);
            if (brand == null){
                throw new IllegalArgumentException("Item requires a brand");
            }
        }

        if (contentValues.containsKey(nachosEntry.COLUMN_PRICE)){
            Integer price = contentValues.getAsInteger(nachosEntry.COLUMN_PRICE);
            if (price != null && price < 0 ) {
                throw new IllegalArgumentException("Item requires a price");
            }
        }

        if (contentValues.containsKey(nachosEntry.COLUMN_QUANTITY)){
            Integer quantity = contentValues.getAsInteger(nachosEntry.COLUMN_QUANTITY);
            if (quantity != null && quantity < 0 ) {
                throw new IllegalArgumentException("Item requires a quantity");
            }
        }

        if (contentValues.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = dBHelper.getWritableDatabase();

        int rowsUpdated = database.update(nachosEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }

}
