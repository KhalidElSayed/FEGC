package com.alorma.megsjc.data.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alorma.megsjc.data.contract.AgrupamentContract;

/**
 * Created by Bernat on 7/10/13.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "meg.db";

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 0 && newVersion == 1) {
            db.execSQL(new AgrupamentContract().create());
        }
    }
}
