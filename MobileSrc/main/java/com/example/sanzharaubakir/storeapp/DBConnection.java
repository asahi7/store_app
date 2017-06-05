package com.example.sanzharaubakir.storeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

/**
 * Created by sanzharaubakir on 22.11.16.
 */
public class DBConnection {
    private static final String TAG = "DBConnection";
    ExternalDbOpenHelper dbOpenHelper;
    SQLiteDatabase database;
    private static String temporaryProductsList = "temporaryProductsList"; // table names
    private static  String id = "ID", productCode = "productCode", count = "count"; // field names
    Context context;
    public DBConnection(Context ctx)
    {
        context = ctx;
    }



    private void dbOpen() {
        dbOpenHelper = new ExternalDbOpenHelper(context, "newDB");
        database = dbOpenHelper.getWritableDatabase();
    }
    private void dbClose(){
        database.close();
    }

    public void store_temporary_product(Map<String, String> product)
    {
        dbOpen();
        if (!checkExcistence(temporaryProductsList))
            createTemporaryProductsListTable();
        ContentValues cv = new ContentValues();
        cv.put(productCode, product.get(productCode));
        cv.put(count, product.get(count));
        database.insert(temporaryProductsList, null, cv);
        dbClose();
    }

    private void createTemporaryProductsListTable() {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + temporaryProductsList +
                " (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                productCode + " TEXT, " + count + " INTEGER)");
    }
    public void clearTemporaryProductsTable()
    {
        database.delete(temporaryProductsList, null, null);
    }

    private Boolean checkExcistence(String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
