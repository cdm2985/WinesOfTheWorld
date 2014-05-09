package com.martinez.winesoftheworld.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.wines.Wine;

/**
 * Created by Chris on 5/8/14.
 */
public class WineListArrayAdapter extends ArrayAdapter<Wine> {

    Context context;
    int layoutResourceId;
    Wine wines[] = null;

    public WineListArrayAdapter(Context context, int layoutResourceId, Wine[] wines) {
        super(context, layoutResourceId, wines);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.wines = wines;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            WineRowHolder holder = null;

            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new WineRowHolder();
                holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
                holder.subText = (TextView)row.findViewById(R.id.subText);
                row.setTag(holder);
            }
            else
            {
                holder = (WineRowHolder)row.getTag();
            }

            Wine wine = wines[position];
            holder.txtTitle.setText(wine.getName());
            holder.subText.setText(wine.getGrape());

            return row;

    }

    static class WineRowHolder
    {
        TextView txtTitle;
        TextView subText;
    }
}
