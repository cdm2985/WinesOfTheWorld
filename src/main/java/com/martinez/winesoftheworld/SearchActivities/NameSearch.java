package com.martinez.winesoftheworld.SearchActivities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.Views.WineResult;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import java.util.ArrayList;

public class NameSearch extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private WineControl wineControl = MainActivity.wineControl;
    private ListView wineList = null;
    private WineListArrayAdapter mAdapter;
    private SearchView searchView;
    private ArrayList<Wine> winesArrayList = wineControl.getWines();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView nameSearch = (SearchView) findViewById(R.id.searchView);

        searchView = (SearchView) findViewById(R.id.searchView);

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new WineListArrayAdapter(this, R.layout.wine_list_layout, winesArrayList, Search.NAME_SEARCH_CALL ));
        mAdapter.notifyDataSetChanged();
        wineList.setOnItemClickListener( setupAdapter() );

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Name");

        setupSearchView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
        newText = newText.trim();
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

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @TargetApi(11)
    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
    }

    private AdapterView.OnItemClickListener setupAdapter(){
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

    public void onBackPressed(){
        mAdapter.getFilter().filter("");
        mAdapter.notifyDataSetChanged();
        super.onBackPressed();
    }
}
