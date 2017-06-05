package com.example.sanzharaubakir.storeapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AwesomeDevelop on 24.12.2014.
 */
public class ExternalDbOpenHelper extends SQLiteOpenHelper
{

    public ExternalDbOpenHelper(Context context, String name){
        super(context, name,null,1);
    }
    public void onCreate(SQLiteDatabase db){

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
// TODO Auto-generated method stub

        onCreate(db);
    }
}
