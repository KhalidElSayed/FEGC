package com.alorma.megsjc.ui.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.alorma.megsjc.R;
import com.alorma.megsjc.data.contract.AgrupamentContract;
import com.alorma.megsjc.service.ParseKmlService;
import com.alorma.megsjc.ui.fragment.AgrupamentsMapFragment;
import com.alorma.megsjc.ui.listeners.Searchable;
import com.alorma.megsjc.utils.AppUtils;
import com.alorma.megsjc.utils.Loaders;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor>, SearchView.OnSuggestionListener {

    private static final String EXTRA_QUERY = "EXTRA_QUERY";

    private SearchView searchView;
    private Searchable mapSearchable;
    private SimpleCursorAdapter suggestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppUtils.checkFirstTime(this)) {
            Intent intent = new Intent(this, ParseKmlService.class);
            intent.putExtra(ParseKmlService.EXTRA_FILENAME, "agrupaments_meg.xml");
            startService(intent);

            AppUtils.saveFirstTime(this);
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AgrupamentsMapFragment agrupamentsMapFragment = new AgrupamentsMapFragment();
        mapSearchable = agrupamentsMapFragment;
        ft.replace(android.R.id.content, agrupamentsMapFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItemSearch = menu.findItem(R.id.action_search);

        if (menuItemSearch != null) {
            searchView = (SearchView) menuItemSearch.getActionView();

            if (searchView != null) {
                searchView.setOnQueryTextListener(this);

                String[] from = new String[]{AgrupamentContract.Fields.NAME};
                int[] to = new int[]{android.R.id.text1};

                suggestionsAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
                searchView.setSuggestionsAdapter(suggestionsAdapter);

                searchView.setOnSuggestionListener(this);

                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            }
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mapSearchable != null) {
            mapSearchable.doSearch(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.length() > 0) {
            Log.i("TAG", "Change: " + query);
            Bundle args = new Bundle();
            args.putString(EXTRA_QUERY, query);
            getLoaderManager().restartLoader(Loaders.AGRUPAMENTS_SEARCH_ACTIVITY_LOADER, args, this);
        } else {
            Log.i("TAG", "Change: null");
            try {
                suggestionsAdapter.swapCursor(null);
            } catch (Exception e) {

            }
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Loaders.AGRUPAMENTS_SEARCH_ACTIVITY_LOADER) {
            if (args.containsKey(EXTRA_QUERY)) {
                String query = args.getString(EXTRA_QUERY);
                return new CursorLoader(this, AgrupamentContract.SEARCH_URI, null, query, null, null);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == Loaders.AGRUPAMENTS_SEARCH_ACTIVITY_LOADER) {
            if (!data.isClosed()) {
                try {
                    suggestionsAdapter.swapCursor(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == Loaders.AGRUPAMENTS_SEARCH_ACTIVITY_LOADER) {
            try {
                suggestionsAdapter.swapCursor(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        if (suggestionsAdapter != null) {
            long id = suggestionsAdapter.getItemId(position);
            mapSearchable.doSingleSearch(id);
        }
        return true;
    }
}
