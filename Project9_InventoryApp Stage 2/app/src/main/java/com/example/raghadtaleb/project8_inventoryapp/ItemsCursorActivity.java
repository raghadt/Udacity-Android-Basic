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


//------------------------ bindView --------------------------
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameView = view.findViewById(R.id.item_name);
        TextView suppView = view.findViewById(R.id.item_supp);
        TextView priceView =  view.findViewById(R.id.item_price);
        TextView quantityView = view.findViewById(R.id.item_quantity);


        int nameIndex = cursor.getColumnIndex(nachosEntry.COLUMN_NACHOS_NAME);
        int supplierIndex = cursor.getColumnIndex(nachosEntry.COLUMN_SUPPLIER);
        int quantityIndex = cursor.getColumnIndex(nachosEntry.COLUMN_QUANTITY);
        int priceIndex = cursor.getColumnIndex(nachosEntry.COLUMN_PRICE);

        String name = cursor.getString(nameIndex);
        String supplier = cursor.getString(supplierIndex);
        String  quant = cursor.getString(quantityIndex);
        String price = cursor.getString(priceIndex);



        nameView.setText(name);
        suppView.setText(supplier);
        priceView.setText(price);
        quantityView.setText(quant);
    }
}
