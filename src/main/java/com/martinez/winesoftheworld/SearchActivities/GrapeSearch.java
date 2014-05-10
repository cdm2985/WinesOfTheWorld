package com.martinez.winesoftheworld.SearchActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.Views.WineResult;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import java.util.ArrayList;

public class GrapeSearch extends ActionBarActivity  {


    private Spinner grapeType, grapesDropdown;
    private ListView wineList;
    private WineListArrayAdapter mAdapter;
    private WineControl wineControl = MainActivity.wineControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grape_search);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Grape");

        //setup listview of wines
        setupSpinners();

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new WineListArrayAdapter(this, R.layout.wine_list_layout, wineControl.getWines(), Search.GRAPE_SEARCH_CALL));
        wineList.setOnItemClickListener( getOnClickListener() );
    }

    private String[] getAllWines(){
        ArrayList<Wine> wines = wineControl.getWines();
        String[] namesArray = new String[ wines.size() ];
        int counter = 0;
        for( Wine wine: wines ){
            namesArray[counter] =  wine.getName();
            System.out.println( "Adding: " + wine.getName() );
            counter++;
        }
        return namesArray;
    }

    private void setupSpinners(){
        grapeType = (Spinner) findViewById(R.id.grapeTypeDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grape_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grapeType.setAdapter( adapter );
        grapeType.setOnItemSelectedListener( new GrapeTypeListener( this, adapter ));

        grapesDropdown = (Spinner) findViewById(R.id.grapesDropdown);
        ArrayAdapter<CharSequence> grapeDropdownAdapter = ArrayAdapter.createFromResource(this,
                R.array.red_grape_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grapesDropdown.setAdapter( grapeDropdownAdapter );
        grapesDropdown.setOnItemSelectedListener( new GrapeDropdownListener( this, grapeDropdownAdapter ) );
    }

    class GrapeTypeListener implements AdapterView.OnItemSelectedListener{
        private ArrayAdapter<CharSequence> dropdownAdapter = null;
        private android.content.Context context;
        public GrapeTypeListener( android.content.Context _context, ArrayAdapter<CharSequence> _dropdownAdapter ){
            this.dropdownAdapter = _dropdownAdapter;
            this.context = _context;
        }

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            if( ( (String) parent.getItemAtPosition(pos)).equals("Any")){
                dropdownAdapter = ArrayAdapter.createFromResource(context,
                        R.array.any_grape, android.R.layout.simple_spinner_item);
            } else if( ( (String) parent.getItemAtPosition(pos)).equals("Red") ){
                //System.out.println("Red is selected");
                dropdownAdapter = ArrayAdapter.createFromResource(context,
                        R.array.red_grape_array, android.R.layout.simple_spinner_item);
                //grapesDropdown.setAdapter( dropdownAdapter );
            } else {
                //System.out.println("Red is not selected.");
                dropdownAdapter = ArrayAdapter.createFromResource(context,
                        R.array.white_grape_array, android.R.layout.simple_spinner_item);
                //grapesDropdown.setAdapter( dropdownAdapter );
            }
            grapesDropdown.setAdapter( dropdownAdapter );
        }

        public void onNothingSelected(AdapterView<?> parent) {
            wineList.setAdapter(mAdapter = new WineListArrayAdapter(context, R.layout.wine_list_layout, wineControl.getWines(), Search.GRAPE_SEARCH_CALL));
        }
    }

    class GrapeDropdownListener  implements AdapterView.OnItemSelectedListener{
        private ArrayAdapter<CharSequence> dropdownAdapter = null;
        private android.content.Context context;
        public GrapeDropdownListener( android.content.Context _context, ArrayAdapter<CharSequence> _dropdownAdapter ){
            this.dropdownAdapter = _dropdownAdapter;
            this.context = _context;
        }

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            ArrayList<Wine> wines = null;
            if( ((String) parent.getItemAtPosition(pos)).contains("Any") ){
                wines = wineControl.getWines();
            } else {
                wines = wineControl.searchByGrape((String) parent.getItemAtPosition(pos));
            }
            wineList.setAdapter(mAdapter = new WineListArrayAdapter(context, R.layout.wine_list_layout, wines, Search.GRAPE_SEARCH_CALL));
        }

        public void onNothingSelected(AdapterView<?> parent) {
            wineList.setAdapter(mAdapter = new WineListArrayAdapter(context, R.layout.wine_list_layout, wineControl.getWines(), Search.GRAPE_SEARCH_CALL));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grape_search, menu);
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
                Toast.makeText(getApplicationContext(), "Clicked on: " + ((Wine) parent.getItemAtPosition(position)).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent( getApplicationContext(), WineResult.class );

                intent.putExtra( "wine", (Wine) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        };
        return listener;    }
}
