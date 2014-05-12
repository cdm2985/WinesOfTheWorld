package com.martinez.winesoftheworld.SearchActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.Views.WineResult;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import java.util.ArrayList;

public class VintageSearch extends ActionBarActivity {
    private WineControl wineControl = MainActivity.wineControl;
    private EditText earlyYear, lateYear;
    private ArrayList<Wine> wines = null;
    private ListView wineList = null;
    private WineListArrayAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vintage_search);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Year");

        earlyYear = (EditText) findViewById(R.id.earlyYear);
        earlyYear.addTextChangedListener(new ChangeListener());
        lateYear = (EditText) findViewById(R.id.lateYear);
        lateYear.addTextChangedListener(new ChangeListener());

        wines = wineControl.getWines();

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new WineListArrayAdapter(this, R.layout.wine_list_layout, wines, Search.VINTAGE_SEARCH_CALL ));
        mAdapter.notifyDataSetChanged();
        wineList.setOnItemClickListener(getOnClickListener());
    }

    class ChangeListener implements TextWatcher {

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String earlyYearText = earlyYear.getText().toString();
            String lateYearText = lateYear.getText().toString();

            System.out.println("Got called");

            int high = 0;
            int low = 0;

            if(!earlyYearText.isEmpty()){
                low = Integer.parseInt(earlyYearText);
            }

            if(!lateYearText.isEmpty()){
                high = Integer.parseInt(lateYearText);
            }

            System.out.println("High: " + high);
            System.out.println("Low: " + low);

            if(high == 0.0 && low == 0.0){
                wines = wineControl.getWines();
            }else{
                wines = wineControl.searchByVintage(low, high);
            }

            System.out.println("Wines array: " + wines.size());

            for(Wine w : wineControl.getWines()){
                mAdapter.remove(w);
            }

            for(Wine w : wines){
                mAdapter.add(w);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vintage_search, menu);
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
}
