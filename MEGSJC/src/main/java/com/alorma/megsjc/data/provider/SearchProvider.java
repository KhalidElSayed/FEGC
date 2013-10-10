package com.alorma.megsjc.data.provider;

import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.data.model.Agrupament;
import com.alorma.megsjc.data.provider.base.MinionContentProvider;

/**
 * Created by Bernat on 9/10/13.
 */
public class SearchProvider extends MinionContentProvider {
    @Override
    public String getBasePath() {
        return AgrupamentContract.SEARCH_PATH;
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String where = AgrupamentContract.Fields.CP + " LIKE '%" + selection + "%'" +
                " OR " + AgrupamentContract.Fields.NAME + " LIKE '%" + selection + "%'" +
                " OR " + AgrupamentContract.Fields.POBLACIO + " LIKE '%" + selection + "%'" +
                " OR " + AgrupamentContract.Fields.ADDR + " LIKE '%" + selection + "%'";

        return db.query(AgrupamentContract.TABLE, null, where, null, null, null, null);
    }

    @Override
    public long insert(SQLiteDatabase db, Uri uri, ContentValues contentValues) {
        return 0;
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String where, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String where, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType() {
        return AgrupamentContract.CONTENT_SEARCH_TYPE;
    }

    @Override
    public String[] getAvailableColumns() {
        return new String[0];
    }
}
