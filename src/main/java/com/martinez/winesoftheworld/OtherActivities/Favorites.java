package com.martinez.winesoftheworld.OtherActivities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.martinez.winesoftheworld.R;

/**
 * Created by Chris on 5/11/2014.
 */
public class Favorites extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setSubtitle("Your Favorite Wines");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorites_options, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
