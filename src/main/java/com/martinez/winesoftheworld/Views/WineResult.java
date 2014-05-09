package com.martinez.winesoftheworld.Views;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

/**
 * Created by Chris on 5/8/14.
 */
public class WineResult extends ActionBarActivity implements ActionBar.OnNavigationListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wine_result);

        //name
        TextView nameText = (TextView) findViewById( R.id.nameOfWine );
        //grape
        TextView grapeText = (TextView) findViewById( R.id.grape );
        //region
        TextView regionText = (TextView) findViewById( R.id.region );
        //country
        TextView countryText = (TextView) findViewById( R.id.country );
        //price
        TextView priceText = (TextView) findViewById( R.id.price );
        //vintage
        TextView vintageText = (TextView) findViewById( R.id.vintageText );

        Wine wineFromSearch = (Wine) getIntent().getSerializableExtra("wine");

        nameText.setText( wineFromSearch.getName() );
        grapeText.setText( wineFromSearch.getGrape() );
        regionText.setText( wineFromSearch.getRegion() );
        countryText.setText( wineFromSearch.getCountry() );
        priceText.setText( Double.toString( wineFromSearch.getPrice() ) );
        vintageText.setText( wineFromSearch.getVintage() );

        // Set up the action bar to show a dropdown list.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Wine Details for " + wineFromSearch.getName());
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}
