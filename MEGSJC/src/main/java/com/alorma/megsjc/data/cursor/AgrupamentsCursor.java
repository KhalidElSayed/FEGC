package com.alorma.megsjc.data.cursor;

import android.content.ContentValues;
import android.database.Cursor;

import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.data.model.Agrupament;

/**
 * Created by Bernat on 7/10/13.
 */
public class AgrupamentsCursor extends BaseCursorHelper<Agrupament> {

    @Override
    public ContentValues setValues(Agrupament item) {
        ContentValues values = new ContentValues();

        values.put(AgrupamentContract.Fields.NAME, item.getName());
        values.put(AgrupamentContract.Fields.ADDR, item.getAddr());
        values.put(AgrupamentContract.Fields.CP, item.getCp());
        values.put(AgrupamentContract.Fields.POBLACIO, item.getPoblacio());
        values.put(AgrupamentContract.Fields.EMAIL, item.getEmail());
        values.put(AgrupamentContract.Fields.WEB, item.getWeb());
        values.put(AgrupamentContract.Fields.LOGO, item.getLogo());
        values.put(AgrupamentContract.Fields.DEMARC, item.getDemarcacio());
        values.put(AgrupamentContract.Fields.SOTS, item.getSots());
        values.put(AgrupamentContract.Fields.LAT, item.getLat());
        values.put(AgrupamentContract.Fields.LON, item.getLon());

        return values;
    }

    @Override
    public Agrupament readCursor(Cursor cursor) {
        Agrupament agrupament = new Agrupament();

        agrupament.setName(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.NAME)));
        agrupament.setAddr(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.ADDR)));
        agrupament.setCp(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.CP)));
        agrupament.setPoblacio(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.POBLACIO)));
        agrupament.setEmail(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.EMAIL)));
        agrupament.setWeb(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.WEB)));
        agrupament.setLogo(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.LOGO)));
        agrupament.setDemarcacio(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.DEMARC)));
        agrupament.setSots(cursor.getString(cursor.getColumnIndex(AgrupamentContract.Fields.SOTS)));

        agrupament.setLat(cursor.getDouble(cursor.getColumnIndex(AgrupamentContract.Fields.LAT)));
        agrupament.setLon(cursor.getDouble(cursor.getColumnIndex(AgrupamentContract.Fields.LON)));

        return agrupament;
    }
}
