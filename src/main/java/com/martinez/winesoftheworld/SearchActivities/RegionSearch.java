package com.martinez.winesoftheworld.SearchActivities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.Views.WineResult;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegionSearch extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private ListView wineList = null;
    private WineListArrayAdapter mAdapter;
    private SearchView searchView;
    private WineControl wineControl = MainActivity.wineControl;
    private ArrayList<Wine> wines = wineControl.getWines();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Region Name");

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new WineListArrayAdapter(this, R.layout.wine_list_layout, wines, Search.REGION_SEARCH_CALL ));
        mAdapter.notifyDataSetChanged();
        wineList.setOnItemClickListener(getOnClickListener());

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Region");

        setupSearchView();
    }

    public boolean onQueryTextChange(String newText) {
        System.out.println( newText );
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

    protected AdapterView.OnItemClickListener getOnClickListener(){
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked on: " + ((Wine) parent.getItemAtPosition(position)).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent( getApplicationContext(), WineResult.class );

                intent.putExtra( "wine", (Wine) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        };
        return listener;
    }

    @TargetApi(11)
    protected void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
