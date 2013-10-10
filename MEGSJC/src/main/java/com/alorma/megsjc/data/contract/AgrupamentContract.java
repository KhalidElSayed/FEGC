package com.alorma.megsjc.data.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.nfc.tech.NfcA;
import android.provider.BaseColumns;

import com.alorma.megsjc.data.provider.AppProvider;
import com.alorma.megsjc.utils.BaseContract;
import com.alorma.megsjc.utils.DbUtils;

/**
 * Created by Bernat on 7/10/13.
 */
public class AgrupamentContract implements BaseContract{

    public static final String TABLE = "agrupaments";
    public static final String PATH = TABLE;
    public static final String ITEM_PATH = PATH + "/#";
    public static final String SEARCH_PATH = "search";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AppProvider.AUTHORITY + "/" + PATH);
    public static final Uri CONTENT_ITEM_URI = Uri.parse("content://" + AppProvider.AUTHORITY + "/" + ITEM_PATH);
    public static final Uri SEARCH_URI = Uri.parse("content://" + AppProvider.AUTHORITY + "/" + SEARCH_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.android." + PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.android." + PATH;
    public static final String CONTENT_SEARCH_TYPE = CONTENT_TYPE;

    public static class Fields implements BaseColumns {
        public static final String NAME = "NAME";
        public static final String ADDR = "ADDR";
        public static final String CP = "CP";
        public static final String POBLACIO = "POBLACIO";
        public static final String EMAIL = "EMAIL";
        public static final String WEB = "WEB";
        public static final String LOGO = "LOGO";
        public static final String DEMARC = "DEMARCACIO";
        public static final String SOTS = "SOTS";
        public static final String LAT = "LAT";
        public static final String LON = "LON";
    }


    @Override
    public String create() {
        DbUtils dbUtils = new DbUtils(TABLE);

        dbUtils.addParam(Fields.NAME);
        dbUtils.addParam(Fields.ADDR);
        dbUtils.addParam(Fields.CP);
        dbUtils.addParam(Fields.POBLACIO);
        dbUtils.addParam(Fields.EMAIL);
        dbUtils.addParam(Fields.WEB);
        dbUtils.addParam(Fields.DEMARC);
        dbUtils.addParam(Fields.SOTS);
        dbUtils.addParam(Fields.LOGO);
        dbUtils.addParam(Fields.LAT);
        dbUtils.addParam(Fields.LON);

        return dbUtils.create();
    }
}
