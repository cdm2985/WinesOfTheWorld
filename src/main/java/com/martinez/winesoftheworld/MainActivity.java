package com.martinez.winesoftheworld;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.martinez.winesoftheworld.OtherActivities.Favorites;
import com.martinez.winesoftheworld.wines.WineControl;

public class MainActivity extends ActionBarActivity {

    public static WineControl wineControl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load up all the Wines
        AssetManager assMan = getAssets();
        try{
        wineControl = new WineControl( assMan.open("wines.csv"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void searchWines(View view){
        Intent intent = new Intent( this, Search.class );
        intent.putExtra( "WineControl", wineControl );
        startActivity(intent);
    }

    public void seeFavorites(View view){
        Intent intent = new Intent( this, Favorites.class );
        startActivity(intent);
    }
}
