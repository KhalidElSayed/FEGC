package com.alorma.megsjc.ui.fragment;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.ui.listeners.Searchable;
import com.alorma.megsjc.utils.CursorUtils;
import com.alorma.megsjc.utils.Loaders;
import com.google.android.gms.internal.cu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Bernat on 9/10/13.
 */
public class AgrupamentsMapFragment extends MapFragment implements Searchable, LoaderManager.LoaderCallbacks<Cursor>, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener, GoogleMap.OnMyLocationButtonClickListener {


    private static final String EXTRA_QUERY = "EXTRA_QUERY";
    private static final String EXTRA_ITEM_URI = "EXTRA_ITEM_URI";

    private String latField = AgrupamentContract.Fields.LAT;
    private String lngField = AgrupamentContract.Fields.LON;
    private GoogleMap map;
    private boolean firstTime = true;
    private CameraPosition cameraPosition;

    private double lats = 0.0;
    private double lons = 0.0;
    private int i = 0;
    private Cursor cursor;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map = getMap();
        map.setOnMyLocationButtonClickListener(this);
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(this);
        map.setOnCameraChangeListener(this);

        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (getLoaderManager() != null) {
            getLoaderManager().restartLoader(Loaders.AGRUPAMENTS_MAP, getArguments(), this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Loaders.AGRUPAMENTS_SEARCH_MAP_LOADER) {
            String query = "";
            if (args.containsKey(EXTRA_QUERY)) {
                query = args.getString(EXTRA_QUERY);
            }
            return new CursorLoader(getActivity(), AgrupamentContract.SEARCH_URI, null, query, null, null);
        } else if (id == Loaders.AGRUPAMENTS_ITEM_LOADER) {
            Uri uri = args.getParcelable(EXTRA_ITEM_URI);
            return new CursorLoader(getActivity(), uri, null, null, null, null);
        } else {
            return new CursorLoader(getActivity(), AgrupamentContract.CONTENT_URI, null, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        this.cursor = cursor;
        if (cursorLoader.getId() != Loaders.AGRUPAMENTS_MAP) {
            map.setOnMyLocationChangeListener(null);
            map.setMyLocationEnabled(false);
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                map.clear();
                do {
                    MarkerOptions opt = cursorToMarker(cursor);

                    map.addMarker(opt);
                } while (cursor.moveToNext());

                lats = lats / i;
                lons = lons / i;

                LatLng center = new LatLng(lats, lons);

                map.animateCamera(CameraUpdateFactory.newLatLng(center));

                i = 0;
                lats = 0.0;
                lons = 0.0;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        map.clear();

        if (cursor != null) {
            cursor.close();
        }
    }

    private MarkerOptions cursorToMarker(Cursor cursor) {

        CursorUtils cu = new CursorUtils(cursor);

        LatLng latLng = new LatLng(cu.getDouble(latField), cu.getDouble(lngField));

        MarkerOptions mo = new MarkerOptions().position(latLng);

        if (cu.getString(AgrupamentContract.Fields.NAME) != null && !cu.getString(AgrupamentContract.Fields.NAME).equals("")) {
            mo.title(cu.getString(AgrupamentContract.Fields.NAME));
        }

        mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        lats += mo.getPosition().latitude;
        lons += mo.getPosition().longitude;
        i++;

        return mo;
    }

    @Override
    public void onMyLocationChange(Location location) {

        this.cameraPosition = CameraPosition.fromLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()),
                14);

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        map.setOnMyLocationChangeListener(null);

        this.cameraPosition = cameraPosition;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (cameraPosition != null) {
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        map.setOnMyLocationChangeListener(this);
        return true;
    }

    @Override
    public void doSearch(String query) {
        if (getLoaderManager() != null) {
            Bundle args = new Bundle();
            args.putString(EXTRA_QUERY, query);
            getLoaderManager().restartLoader(Loaders.AGRUPAMENTS_SEARCH_MAP_LOADER, args, this);
        }
    }

    @Override
    public void doSingleSearch(long id) {
        Uri uri = ContentUris.withAppendedId(AgrupamentContract.CONTENT_ITEM_URI, id);

        if (getLoaderManager() != null) {
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_ITEM_URI, uri);
            getLoaderManager().restartLoader(Loaders.AGRUPAMENTS_ITEM_LOADER, args, this);
        }
    }
}
