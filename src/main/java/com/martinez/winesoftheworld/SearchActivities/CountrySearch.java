package com.martinez.winesoftheworld.SearchActivities;

import android.annotation.TargetApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class CountrySearch extends NameSearch implements SearchView.OnQueryTextListener {

    private ListView wineList = null;
    private WineListArrayAdapter mAdapter;
    private SearchView searchView;
    private WineControl wineControl = MainActivity.wineControl;
    private ArrayList<Wine> wines = wineControl.getWines();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView nameSearch = (SearchView) findViewById(R.id.searchView);

        searchView = (SearchView) findViewById(R.id.searchView);

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new WineListArrayAdapter(this, R.layout.wine_list_layout, wines, Search.COUNTRY_SEARCH_CALL ));
        mAdapter.notifyDataSetChanged();
        wineList.setOnItemClickListener(setupAdapter());

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Country");

        setupSearchView();
    }

    public boolean onQueryTextChange(String newText) {
        newText = newText.trim();
        System.out.println( newText );
        if (TextUtils.isEmpty(newText)) {
            //wineList.clearTextFilter();
            mAdapter.getFilter().filter("");
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.getFilter().filter( newText );
            mAdapter.notifyDataSetChanged();
            //wineList.addView( );
        }
        return true;
    }
}
