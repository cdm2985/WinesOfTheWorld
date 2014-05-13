package com.martinez.winesoftheworld.OtherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.Views.WineResult;
import com.martinez.winesoftheworld.wines.Wine;

import java.util.ArrayList;

/**
 * Created by Chris on 5/11/2014.
 */
public class Favorites extends ActionBarActivity {
    private WineListArrayAdapter mAdapter = null;
    private  ArrayList<Wine> wines = null;
    private ArrayList<Wine> favoriteWines = null;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        boolean favoritesAvailable = false;
        wines = MainActivity.wineControl.getWines();
        for( Wine wine: wines ){
            if( wine.isFavorite() ){
                favoritesAvailable = true;
                break;
            }
        }

        updateView( favoritesAvailable );

        favoriteWines = new ArrayList<Wine>();
        for( Wine wine: wines ){
            if( wine.isFavorite() ){
                favoriteWines.add( wine );
            }
        }
        ListView wineResults = (ListView) findViewById(R.id.wineResults);
        wineResults.setAdapter(mAdapter = new WineListArrayAdapter(this, R.layout.wine_list_layout, favoriteWines, Search.FAVORITES_SEARCH_CALL ) );
        wineResults.setOnItemClickListener( getOnClickListener() );

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setSubtitle("Your Favorite Wines");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorites_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected AdapterView.OnItemClickListener getOnClickListener(){
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent( getApplicationContext(), WineResult.class );

                intent.putExtra( "wine", (Wine) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        };
        return listener;
    }

    protected void onResume(){
        super.onResume();
        updateFavorites();
        updateView( (favoriteWines.size() > 0 ));
        mAdapter.notifyDataSetChanged();
    }

    protected void onRestart(){
        super.onRestart();
        updateFavorites();
        updateView( (favoriteWines.size() > 0 ) );
        mAdapter.notifyDataSetChanged();
    }

    private void updateFavorites(){
        favoriteWines.clear();
        for( Wine wine: MainActivity.wineControl.getWines() ){
            if( wine.isFavorite() ){
                favoriteWines.add( wine );
            }
        }
    }

    private void updateView( boolean favoritesAvailable ){
        ListView wineResults = (ListView) findViewById(R.id.wineResults);
        TextView favoriteText = (TextView) findViewById(R.id.noFav);

        if(!favoritesAvailable){
            wineResults.setVisibility(View.INVISIBLE);
            favoriteText.setVisibility(View.VISIBLE);
        } else {
            wineResults.setVisibility(View.VISIBLE);
            favoriteText.setVisibility(View.INVISIBLE);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.search:
                searchWines();
                return true;
            case R.id.addWine:
                addWine();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchWines(){

    }

    private void addWine(){
        Intent intent = new Intent( getApplicationContext(), AddWine.class );
        startActivity(intent);
    }
}
