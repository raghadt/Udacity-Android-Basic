package com.example.raghadtaleb.project8_inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.raghadtaleb.project8_inventoryapp.data.Contract.nachosEntry;

/**
 * Created by raghadtaleb on 02/02/2018.
 */

public class ItemsCursorActivity extends CursorAdapter {

    public ItemsCursorActivity(Context context, Cursor c) {
        super(context, c, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
    }


    public ItemsCursorActivity(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ItemsCursorActivity(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


//--------------------------------------------------
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textNameView = view.findViewById(R.id.item_name);
        TextView textBrandView = view.findViewById(R.id.item_brand);
        TextView textPriceView =  view.findViewById(R.id.item_price);
        TextView textQuantityView = view.findViewById(R.id.item_quantity);

        //find the columns of item attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_NACHOS_NAME);
        int brandColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPPLIER);
        int priceColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(nachosEntry.COLUMN_QUANTITY);

        //Read the item attributes from the Cursor for the current item
        String itemName = cursor.getString(nameColumnIndex);
        String itemBrand = cursor.getString(brandColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);

        //Populate fields with extracted properties
        textNameView.setText(itemName);
        textBrandView.setText(itemBrand);
        textPriceView.setText(itemPrice);
        textQuantityView.setText(itemQuantity);
    }
}
