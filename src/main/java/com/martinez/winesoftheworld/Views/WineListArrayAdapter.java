package com.martinez.winesoftheworld.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Chris on 5/8/14.
 */
public class WineListArrayAdapter extends ArrayAdapter<Wine> {

    Context context1;
    private WineArrayFilter filter = null;
    private ArrayList<Wine> originalItems = new ArrayList<Wine>();
    private final Object mLock = new Object();
    private ArrayList<Wine> items;
    private int callingActivity = 0;

    public WineListArrayAdapter(Context context, int layoutResourceId, ArrayList<Wine> wines, int callingActivity ) {
        super(context, R.id.wineResults, layoutResourceId, wines);
        this.context1 = context;
        this.items = wines;
        cloneItems(wines);
        this.callingActivity = callingActivity;
    }

    protected void cloneItems(ArrayList<Wine> items) {
        for (Iterator iterator = items.iterator(); iterator
                .hasNext();) {
            Wine gi = (Wine) iterator.next();
            originalItems.add(gi);
        }
    }

    @Override
    public int getCount() {
        synchronized (mLock) {
            return items != null ? items.size() : 0;
        }
    }

    @Override
    public Wine getItem(int item) {
        Wine gi = null;
        synchronized(mLock) {
            gi = items!=null ? items.get(item) : null;

        }
        return gi;
    }

    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WineRowHolder holder = null;
        if (row == null ){
            LayoutInflater inflater = LayoutInflater.from(context1);
            row = inflater.inflate(R.layout.wine_list_layout, null);
        }
        Wine wine = null;

        synchronized(mLock){
            wine = items.get(position);
        }

        if( wine != null) {
            holder = new WineRowHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.subText = (TextView)row.findViewById(R.id.subText);
            row.setTag(holder);
        } else {
            holder = (WineRowHolder)row.getTag();
        }

        holder.txtTitle.setText(wine.getName());
        switch( callingActivity ){
            case( Search.NAME_SEARCH_CALL ):
                holder.subText.setText(wine.getGrape());
                break;
            case ( Search.COUNTRY_SEARCH_CALL ):
                holder.subText.setText(wine.getCountry());
                break;
            case( Search.GRAPE_SEARCH_CALL ):
                holder.subText.setText(wine.getGrape());
                break;
            case( Search.REGION_SEARCH_CALL ):
                holder.subText.setText(wine.getRegion());
                break;
            case( Search.VINTAGE_SEARCH_CALL ):
                System.out.println( wine.getVintage());
                holder.subText.setText(Integer.toString(wine.getVintage()));
                break;
            case( Search.PRICE_SEARCH_CALL ):
                holder.subText.setText( Double.toString(wine.getPrice()));
                break;
            case( Search.TASTING_NOTES_CALL ):
                String[] splitNotes = wine.getTastingNotes().split("\\|");
                StringBuilder notes = new StringBuilder();
                for(String note: splitNotes ){
                    notes.append( note.trim().toLowerCase() );
                    if( note.length() > 0 ) notes.append(", ");
                }
                String text = notes.toString();
                holder.subText.setText(text);
                break;
            case( Search.FAVORITES_SEARCH_CALL ):
                holder.subText.setText(wine.getGrape());
                break;
            default:
                break;
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top );
        row.startAnimation(animation);
        lastPosition = position;

        return row;
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new WineArrayFilter();
        }
        return filter;
    }

    class WineArrayFilter extends Filter{
        //Lightly adapted from this stack overflow help page
        //http://stackoverflow.com/questions/2519317/how-to-write-a-custom-filter-for-listview-with-arrayadapter

        protected FilterResults performFiltering(CharSequence prefix) {
            ArrayList<Wine> items;

            // Initiate our results object
            FilterResults results = new FilterResults();

            // No prefix is sent to filter by so we're going to send back the original array
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                    if( callingActivity == Search.FAVORITES_SEARCH_CALL){
                        ArrayList<Wine> favoriteWines = new ArrayList<Wine>();
                        for( Wine wine: originalItems ){
                            if( wine.isFavorite() ){
                                favoriteWines.add( wine );
                            }
                        }
                        results.values = favoriteWines;
                        results.count = favoriteWines.size();
                    } else {
                        results.values = originalItems;
                        results.count = originalItems.size();
                    }
                }
            } else synchronized (mLock) {
                // Compare lower case strings
                String prefixString = prefix.toString().toLowerCase();
                final ArrayList<Wine> filteredItems = new ArrayList<Wine>();
                // Local to here so we're not changing actual array
                final ArrayList<Wine> localItems = new ArrayList<Wine>();
                localItems.addAll(originalItems);
                final int count = localItems.size();

                for (int i = 0; i < count; i++) {
                    final Wine item = localItems.get(i);
                    String itemDesc = "";
                    switch (callingActivity) {
                        case (Search.NAME_SEARCH_CALL):
                            itemDesc = item.getName().toLowerCase();
                            break;
                        case (Search.COUNTRY_SEARCH_CALL):
                            itemDesc = item.getCountry().toLowerCase();
                            break;
                        case (Search.GRAPE_SEARCH_CALL):
                            itemDesc = item.getGrape().toLowerCase();
                            break;
                        case (Search.REGION_SEARCH_CALL):
                            itemDesc = item.getRegion().toLowerCase();
                            break;
                        case (Search.VINTAGE_SEARCH_CALL):
                            itemDesc = item.getRegion().toLowerCase();
                            break;
                        case (Search.PRICE_SEARCH_CALL):
                            itemDesc = Double.toString(item.getPrice());
                            break;
                        case (Search.TASTING_NOTES_CALL):
                            String[] splitNotes = item.getTastingNotes().split("\\|");
                            StringBuilder notes = new StringBuilder();
                            int counter = 0;
                            for (String note : splitNotes) {
                                if( counter != 0 )
                                    notes.append(", ");
                                notes.append(note.trim().toLowerCase());
                            }
                            System.out.println(itemDesc);
                            itemDesc = notes.toString();
                            break;
                        default:
                            break;
                    }

                    // First match against the whole, non-splitted value
                    if (itemDesc.contains(prefixString)) {
                        filteredItems.add(item);
                    }
                }

                // Set and return
                results.values = filteredItems;
                results.count = filteredItems.size();
            }//end synchronized

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            synchronized(mLock) {
                final ArrayList<Wine> localItems = (ArrayList<Wine>) results.values;
                notifyDataSetChanged();
                clear();
                //Add the items back in
                for (Iterator iterator = localItems.iterator(); iterator
                        .hasNext();) {
                    Wine gi = (Wine) iterator.next();
                    add(gi);
                }
            }//end synchronized
        }
    }

    static class WineRowHolder
    {
        TextView txtTitle;
        TextView subText;
    }
}
