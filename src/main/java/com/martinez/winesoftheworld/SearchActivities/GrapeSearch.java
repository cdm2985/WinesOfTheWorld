package com.martinez.winesoftheworld.SearchActivities;

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

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import java.util.ArrayList;

public class GrapeSearch extends ActionBarActivity  {


    private Spinner grapeType, grapesDropdown;
    private ListView wineList;
    private ArrayAdapter<String> mAdapter;
    private WineControl wineControl = MainActivity.wineControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grape_search);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Search by Grape");

        //get our wines!
        //wineControl = (WineControl) this.getIntent().getSerializableExtra( "WineControl" );

        //setup listview of wines
        setupSpinners();

        wineList = (ListView) findViewById(R.id.wineResults);
        wineList.setAdapter(mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getAllWines()));



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
            if( ( (String) parent.getItemAtPosition(pos)).equals("Red") ){
                System.out.println("Red is selected");
                dropdownAdapter = ArrayAdapter.createFromResource(context,
                        R.array.red_grape_array, android.R.layout.simple_spinner_item);
                //grapesDropdown.setAdapter( dropdownAdapter );
            } else {
                System.out.println("Red is not selected.");
                dropdownAdapter = ArrayAdapter.createFromResource(context,
                        R.array.white_grape_array, android.R.layout.simple_spinner_item);
                //grapesDropdown.setAdapter( dropdownAdapter );
            }
            grapesDropdown.setAdapter( dropdownAdapter );
        }

        public void onNothingSelected(AdapterView<?> parent) {
            wineList.setAdapter(mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, getAllWines()));
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

            ArrayList<Wine> wines = wineControl.searchByGrape((String) parent.getItemAtPosition(pos));

            ArrayList<String> names = new ArrayList<String>();

            String[] namesArray = new String[ wines.size() ];
            int counter = 0;
            for( Wine wine: wines ){
                namesArray[counter] =  wine.getName();
                System.out.println( "Adding: " + wine.getName() );
                counter++;
            }
            wineList.setAdapter(mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, namesArray));
        }

        public void onNothingSelected(AdapterView<?> parent) {
            wineList.setAdapter(mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, getAllWines()));
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


}
