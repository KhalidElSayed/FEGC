package com.alorma.megsjc.ui.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.ui.adapter.AgrupamentsAdapter;

/**
 * Created by Bernat on 7/10/13.
 */
public class AgrupamentsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int AGRUPAMENTS_LIST = 0;
    private AgrupamentsAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLoaderManager().restartLoader(AGRUPAMENTS_LIST, getArguments(), this);

        adapter = new AgrupamentsAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), AgrupamentContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }
}
