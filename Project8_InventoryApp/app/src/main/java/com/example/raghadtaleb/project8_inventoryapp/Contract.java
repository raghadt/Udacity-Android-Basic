package com.example.raghadtaleb.project8_inventoryapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raghadtaleb on 27/01/2018.
 */

public class Contract {
    //    The contract contains at minimum constants for the product name, price, quantity, image, supplier name, supplier email, and supplier phone number.


    public static final String CONTENT_AUTHORITY = "com.example.android.project8_inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "nachos";


    private Contract() {
    }

    public static final class nachosEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        public final static String TABLE_NAME = "nachos";


        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_NACHOS_NAME = "name";

        public final static String COLUMN_PRICE = "price";

        public final static String COLUMN_QUANTITY = "quantity";

        public final static String COLUMN_IMAGE = "images";

        public final static String COLUMN_SUPPLIER = "supplier";

        public final static String COLUMN_SUPP_EMAIL = "email";

        public final static String COLUMN_SUPER_PHONE = "phone";


    }
}


