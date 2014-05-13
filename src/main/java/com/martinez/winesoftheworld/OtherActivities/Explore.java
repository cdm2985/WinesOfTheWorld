package com.martinez.winesoftheworld.OtherActivities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.wines.Wine;

import java.util.ArrayList;

public class Explore extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        ArrayList<Region> regionsArray = createRegionCards();

        ListView regionList = (ListView) findViewById(R.id.countries);
        regionList.setAdapter( new ExploreAdapter(this, R.layout.explore_card, regionsArray ));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.explore, menu);


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

    private ArrayList<Region> createRegionCards(){
        ArrayList<Region> regions = new ArrayList<Region>();
        Region france = new Region();
        france.regionName = "France";
        france.description = "This region is where the most expensive and well known wines are. " +
                "This has a maritime climate which means it's close to a body of water. This allows for moderate temperatures and produces good red grapes.";
        france.typesOfWine = "Bordeaux, Alsace, Burgundy, Champagne, Merlot, many others! ";
        regions.add(france);

        Region us = new Region();
        us.regionName = "United States";
        us.description = "The United States has a lot of regions that include California, Oregon, Washington, and New York State's Finger Lakes. Most of these are new world grapes which provide fruity and flavorful wines! ";
        us.typesOfWine = "Riesling, Chardonnay, Pinot Noir, Cabernet Franc, Gewurztraminer, etc... ";
        regions.add(us);

        return regions;
    }

    class Region{
        public  String regionName = "";
        public String description = "";
        public String typesOfWine = "";
        public Region(){

        }
        public Region( String regionName, String description, String typesOfWine){
            this.regionName = regionName;
            this.description = description;
            this.typesOfWine = typesOfWine;
        }
    }

    class ExploreAdapter extends ArrayAdapter<Region>{
        Context context = null;
        ArrayList<Region> regions = null;
        public ExploreAdapter(Context context, int layoutResourceId, ArrayList<Region> regions){
            super( context, layoutResourceId, regions );
            this.context = context;
            this.regions = regions;
        }

        private int lastPosition = -1;

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ExploreHolder holder = null;
            if (row == null ){
                LayoutInflater inflater = LayoutInflater.from(context);
                row = inflater.inflate(R.layout.explore_card, null);
            }
            Region region = getItem(position);

            if( region != null ) {
                holder = new ExploreHolder();
                holder.regionName = (TextView) row.findViewById(R.id.regionName);
                holder.description = (TextView) row.findViewById(R.id.descriptionOfRegion);
                holder.typesOfWines = (TextView) row.findViewById(R.id.typesOfWines);
            } else {
                holder = (ExploreHolder)row.getTag();
            }
            row.setTag(holder);
            holder.regionName.setText(region.regionName);
            String description = "<b>Description:</b> " + region.description;
            holder.description.setText(Html.fromHtml(description));
            String typesOfWines = "<b>Types of Wines:</b> " + region.typesOfWine;
            holder.typesOfWines.setText(Html.fromHtml(typesOfWines));

            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            row.startAnimation(animation);
            lastPosition = position;

            return row;
        }
    }

    class ExploreHolder{
        TextView regionName;
        TextView description;
        TextView typesOfWines;
    }
}
