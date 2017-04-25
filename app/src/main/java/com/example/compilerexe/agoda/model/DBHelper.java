package com.example.compilerexe.agoda.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by compilerexe on 4/19/2017 AD.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbName = "Agoda.sqlite";
    private static final String tableName = "Customers";
    private static final int dbVersion = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName +
        "(CustomerID INTEGER PRIMARY KEY AUTOINCREMENT," +
        " Email TEXT(40) NOT NULL UNIQUE," +
        " Password TEXT(30) NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
