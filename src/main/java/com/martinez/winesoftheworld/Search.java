package com.martinez.winesoftheworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martinez.winesoftheworld.SearchActivities.CountrySearch;
import com.martinez.winesoftheworld.SearchActivities.GrapeSearch;
import com.martinez.winesoftheworld.SearchActivities.NameSearch;
import com.martinez.winesoftheworld.SearchActivities.NotesSearch;
import com.martinez.winesoftheworld.SearchActivities.PriceSearch;
import com.martinez.winesoftheworld.SearchActivities.RegionSearch;
import com.martinez.winesoftheworld.SearchActivities.VintageSearch;
import com.martinez.winesoftheworld.wines.WineControl;
public class Search extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private WineControl wineControl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        wineControl = (WineControl) this.getIntent().getSerializableExtra( "WineControl" );

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Category");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(android.R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        //getSupportFragmentManager().beginTransaction()
        //        .replace(android.R.id.container, PlaceholderFragment.newInstance(position + 1))
        //        .commit();
        return true;
    }



    public void startNameSearch( View view ){
        System.out.println("Tapped name search.");
        Intent intent = new Intent( this, NameSearch.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }

    public void startCountrySearch( View view ){
        System.out.println("Tapped CountrySearch.");
        Intent intent = new Intent( this, CountrySearch.class );
        intent.putExtra("WineControl", wineControl);
        startActivity(intent);
    }

    public void startGrapeSearch( View view ){
        System.out.println("Tapped GrapeSearch.");
        Intent intent = new Intent( this, GrapeSearch.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }

    public void startVintageSearch( View view ){
        System.out.println("Tapped VintageSearch.");
        Intent intent = new Intent( this, VintageSearch.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }

    public void startPriceSearch( View view ){
        System.out.println("Tapped PriceSearch.");
        Intent intent = new Intent( this, PriceSearch.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }

    public void startRegionSearch( View view ){
        System.out.println("Tapped RegionSearch.");
        Intent intent = new Intent( this, RegionSearch.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }

    public void startNotesSearch( View view ){
        System.out.println("Tapped NotesSearch.");
        Intent intent = new Intent( this, NotesSearch.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }
}
