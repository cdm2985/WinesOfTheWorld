package com.martinez.winesoftheworld.Views;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

/**
 * Created by Chris on 5/8/14.
 */
public class WineResult extends ActionBarActivity implements ActionBar.OnNavigationListener {

    private Wine wineFromSearch = null;
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
        //tasting notes
        TextView tastingNotesText = (TextView) findViewById( R.id.tastingNotes );

        wineFromSearch = (Wine) getIntent().getSerializableExtra("wine");

        nameText.setText( wineFromSearch.getName() );
        grapeText.setText( wineFromSearch.getGrape() );
        regionText.setText( wineFromSearch.getRegion() );
        countryText.setText( wineFromSearch.getCountry() );
        priceText.setText( Double.toString( wineFromSearch.getPrice() ) );
        vintageText.setText( Integer.toString(wineFromSearch.getVintage()) );

        String[] splitNotes = wineFromSearch.getTastingNotes().split("\\|");
        StringBuilder notes = new StringBuilder();
        int counter = 0;
        for (String note : splitNotes) {
            if( counter != 0 )
                notes.append(", ");
            notes.append(note.trim().toLowerCase());
            counter++;
        }

        tastingNotesText.setText( notes.toString() );

        // Set up the action bar to show a dropdown list.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Wine Details for " + wineFromSearch.getName());

    }

    public boolean onPrepareOptionsMenu (Menu menu){
        MenuItem favoriteBtn = menu.findItem(R.id.favorite);

        if(wineFromSearch.isFavorite() ){
            favoriteBtn.setIcon(R.drawable.ic_action_important);
        } else {
            favoriteBtn.setIcon(R.drawable.ic_action_not_important);
        }

        System.out.println(wineFromSearch.isFavorite());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wine_result_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.favorite:
                if( wineFromSearch.isFavorite() ){
                    unFavoriteWine();
                    item.setIcon(R.drawable.ic_action_not_important);
                } else {
                    favoriteWine();
                    item.setIcon(R.drawable.ic_action_important);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void favoriteWine(){
        for(Wine wine: MainActivity.wineControl.getWines()){
            if( wine.getName().equals(wineFromSearch.getName())
                    && wine.getVintage() == wineFromSearch.getVintage() ){
                wine.setFavorite(true);
            }
        }
        Toast.makeText(getApplicationContext(), "Favorited: " + wineFromSearch.getName(), Toast.LENGTH_SHORT).show();

    }

    private void unFavoriteWine(){
        for(Wine wine: MainActivity.wineControl.getWines()){
            if( wine.getName().equals(wineFromSearch.getName())
                    && wine.getVintage() == wineFromSearch.getVintage() ){
                wine.setFavorite(false);
            }
        }
        Toast.makeText(getApplicationContext(), "Unfavorited: " + wineFromSearch.getName(), Toast.LENGTH_SHORT).show();
    }
}
