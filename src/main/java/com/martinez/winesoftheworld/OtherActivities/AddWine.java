package com.martinez.winesoftheworld.OtherActivities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.martinez.winesoftheworld.MainActivity;
import com.martinez.winesoftheworld.R;
import com.martinez.winesoftheworld.Search;
import com.martinez.winesoftheworld.SearchActivities.GrapeSearch;
import com.martinez.winesoftheworld.Views.WineListArrayAdapter;
import com.martinez.winesoftheworld.wines.RedWine;
import com.martinez.winesoftheworld.wines.WhiteWine;
import com.martinez.winesoftheworld.wines.Wine;
import com.martinez.winesoftheworld.wines.WineControl;

import java.util.ArrayList;

/**
 * Created by Chris on 5/12/14.
 */
public class AddWine extends ActionBarActivity {
    private Spinner grapeType = null;
    private Spinner grapesDropdown = null;
    private WineControl wineControl = MainActivity.wineControl;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wine);

        setupSpinners();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_wine_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.addWineDone:
                addWineToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addWineToList(){
        TextView nameOfWine = (TextView) findViewById( R.id.nameOfWine );
        TextView nameOfRegion = (TextView) findViewById( R.id.nameOfRegion );
        TextView nameOfCountry = (TextView) findViewById( R.id.nameOfCountry );
        TextView price = (TextView) findViewById( R.id.priceInput );
        TextView vintage = (TextView) findViewById( R.id.vintageInput );
        TextView notes = (TextView) findViewById( R.id.notesInput );
        grapeType = (Spinner) findViewById( R.id.grapeTypeDropdown );
        grapesDropdown = (Spinner) findViewById( R.id.grapesDropdown );
        if( isTextEmpty( nameOfWine)){
            Toast.makeText(getApplicationContext(), "Name of wine cannot be empty!", Toast.LENGTH_SHORT ).show();
            return;
        }

        Wine wine = null;
        try{
            String _grape = (String) grapeType.getSelectedItem();
            if( _grape.contains("Red") ){
                wine = new RedWine((String) grapesDropdown.getSelectedItem() );
            } else if ( _grape.contains("White") ){
                wine = new WhiteWine((String) grapesDropdown.getSelectedItem() );
            } else {
                wine = new Wine();
            }
            wine.setName( nameOfWine.getText().toString() );
            wine.setRegion(nameOfRegion.getText().toString());
            wine.setCountry(nameOfCountry.getText().toString());
            wine.setPrice(Double.valueOf(price.getText().toString()));
            wine.setVintage(Integer.valueOf(vintage.getText().toString()));
            wine.setTastingNotes(notes.getText().toString());
            wine.setFavorite(true);

            MainActivity.wineControl.addWineToPersonalList( wine );
            Toast.makeText(getApplicationContext(), "Added your new wine: " + wine.getName() + "!", Toast.LENGTH_SHORT ).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed to add wine, check your input!", Toast.LENGTH_SHORT ).show();

        }
    }

    private boolean isTextEmpty(TextView textView ){
        return textView.getText().length() == 0;
    }

    private void setupSpinners(){
        grapeType = (Spinner) findViewById(R.id.grapeTypeDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grape_types_no_any, android.R.layout.simple_spinner_item);
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
            }

        public void onNothingSelected(AdapterView<?> parent) {
            //wineList.setAdapter(mAdapter = new WineListArrayAdapter(context, R.layout.wine_list_layout, wineControl.getWines(), Search.GRAPE_SEARCH_CALL));
        }
    }
}
