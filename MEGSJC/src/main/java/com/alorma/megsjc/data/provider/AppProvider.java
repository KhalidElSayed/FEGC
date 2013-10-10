package com.alorma.megsjc.data.provider;

import android.database.sqlite.SQLiteDatabase;

import com.alorma.megsjc.data.bbdd.DbHelper;
import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.data.provider.base.DespicableContentProvider;
import com.alorma.megsjc.data.provider.base.SimpleItemMinionContentProvider;
import com.alorma.megsjc.data.provider.base.SimpleMinionContentProvider;

/**
 * Created by Bernat on 7/10/13.
 */
public class AppProvider extends DespicableContentProvider {
    public static final String AUTHORITY = "com.alorma.megsjc.data";
    private SQLiteDatabase db;

    @Override
    public void recruitMinions() {
        addMinion(new SimpleMinionContentProvider(AgrupamentContract.PATH, AgrupamentContract.TABLE, AgrupamentContract.CONTENT_TYPE));
        addMinion(new SimpleItemMinionContentProvider(AgrupamentContract.ITEM_PATH, AgrupamentContract.TABLE, AgrupamentContract.CONTENT_ITEM_TYPE));
        addMinion(new SearchProvider());
    }

    @Override
    public String getAuthority() {
        return AUTHORITY;
    }

    @Override
    public SQLiteDatabase getDb() {
        if (db == null) {
            db = new DbHelper(getContext()).getWritableDatabase();
        }
        return db;
    }
}
