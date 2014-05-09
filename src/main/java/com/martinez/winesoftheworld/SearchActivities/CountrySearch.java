package com.martinez.winesoftheworld.SearchActivities;

import android.annotation.TargetApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
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

public class CountrySearch extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private ListView wineList = null;
    private ArrayAdapter<String> mAdapter;
    private SearchView searchView;
    private WineControl wineControl = MainActivity.wineControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_search);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Country");

        //wineControl = (WineControl) this.getIntent().getSerializableExtra( "WineControl" );

        searchView = (SearchView) findViewById(R.id.searchView);

        ArrayList<TextView> listViewsOfWine = new ArrayList<TextView>();

        searchView = (SearchView) findViewById(R.id.searchView);

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getAllWines()));
        setupSearchView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.country_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onQueryTextChange(String newText) {
        ArrayList<Wine> wines = null;
        newText = newText.trim();
        if (TextUtils.isEmpty(newText)) {
            wines = wineControl.getWines();
        } else {
            wines = wineControl.searchByCountry( newText );
        }

        ArrayList<String> names = new ArrayList<String>();

        String[] namesArray = new String[ wines.size() ];
        int counter = 0;
        for( Wine wine: wines ){
            namesArray[counter] =  wine.getName();
            System.out.println( "Adding: " + wine.getName() );
            counter++;
        }
        wineList.setAdapter(mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesArray));
        return true;
    }

    private String[] getAllWines(){
        ArrayList<Wine> wines = wineControl.getWines();
        String[] namesArray = new String[ wines.size() ];
        int counter = 0;
        for( Wine wine: wines ){
            namesArray[counter] =  wine.getName();
            System.out.println( "Adding: " + wine.getName() );
            counter++;
        }
        return namesArray;
}

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @TargetApi(11)
    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        //searchView.set
        //earchView.setQueryHint(getString(R.string.cheese_hunt_hint));
    }
}
