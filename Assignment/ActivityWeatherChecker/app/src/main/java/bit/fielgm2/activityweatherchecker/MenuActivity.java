package bit.fielgm2.activityweatherchecker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.ExecutionException;

public class MenuActivity extends AppCompatActivity
{
    //fields for menu activity
    private SQLiteDatabase locationsDatabase;
    private LocationManager locationManager;
    private Criteria criteria;
    private String providerName;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //open or make database
        locationsDatabase = openOrCreateDatabase("locationsDatabase", MODE_PRIVATE, null);
        createDbTables();

        // Setting up gps
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        providerName = locationManager.getBestProvider(criteria, false);

        try
        {
            locationManager.requestLocationUpdates(providerName, 500, 1, new locationListener());
        }
        catch (SecurityException se)
        {
            Toast.makeText(MenuActivity.this, "App does not have permission to access Location!", Toast.LENGTH_SHORT).show();
        }

        //fills the list for the different options
        fillMenuList();
        //gets the current weather
        CurrentWeatherAsync();
    }

    //calls to get weather info for the current location
    public void CurrentWeatherAsync()
    {
        getLocation();
        LatLng latLng = new LatLng(latitude, longitude);
        String JSONstring = "";
        ProgressDialog showProgress = ProgressDialog.show(MenuActivity.this, "Progress", "Getting Weather Information for current location", true);
        //makes the api call
        AsyncCalls.GetWeatherFromLongLat getWeatherFromLongLat = new AsyncCalls.GetWeatherFromLongLat();
        try {
            JSONstring  = getWeatherFromLongLat.execute(latLng).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //converts the josn to a weather object
        Weather current = JSONProcessors.weatherJSONParser(JSONstring);
        //loads the table with weather info
        loadTable(current);
        showProgress.dismiss();
    }

    //filling drawer listview
    public void fillMenuList()
    {
        String[] menuOptions = {"Show Map", "Add an Activity", "View All Activities"};
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_view, menuOptions);

        ListView menuOptionsListView = (ListView) findViewById(R.id.menu_list_view);
        menuOptionsListView.setOnItemClickListener(new menuListClickListner());
        menuOptionsListView.setAdapter(optionsAdapter);
    }

    //gets the last known location and sets the longitude and latitude
    // variables to the correct values
    public void getLocation()
    {
        try
        {
            Location currentLocation = locationManager.getLastKnownLocation(providerName);

            longitude = currentLocation.getLongitude();
            latitude = currentLocation.getLatitude();
        }
        catch (SecurityException se)
        {
            Toast.makeText(MenuActivity.this, "App does not have permission to access Location!", Toast.LENGTH_SHORT).show();
        }
    }

    //loads the weather information into the appropriate rows in the table
    public void loadTable(Weather currentWeatherInfo)
    {
        TextView tempTxtView = (TextView) findViewById(R.id.tableCurrTemp);
        TextView minTempTxtView = (TextView) findViewById(R.id.tableMinTemp);
        TextView maxTempTxtView = (TextView) findViewById(R.id.tableMaxTemp);
        TextView humidityTxtView = (TextView) findViewById(R.id.tableHumidity);
        TextView pressureTxtView = (TextView) findViewById(R.id.tablePressure);

        tempTxtView.setText(String.valueOf(currentWeatherInfo.getCurrentTemp()));
        minTempTxtView.setText(String.valueOf(currentWeatherInfo.getMinTemp()));
        maxTempTxtView.setText(String.valueOf(currentWeatherInfo.getMaxTemp()));
        humidityTxtView.setText(String.valueOf(currentWeatherInfo.getHumidity()));
        pressureTxtView.setText(String.valueOf(currentWeatherInfo.getPressure()));
    }

    //creates the table for the database if it does not already exist
    private void createDbTables()
    {

        String createTablesQuery = "CREATE TABLE IF NOT EXISTS tblLocations(" +
                "loactionsID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "activityName TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "latitude TEXT, " +
                "longitude TEXT, " +
                "cityName TEXT);";

        locationsDatabase.execSQL(createTablesQuery);

        locationsDatabase.close();
    }


    //=======================================
    //Inner Classes
    //=======================================

    //listens for the location
    private class locationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


    //adapter click for the menu list
    public class menuListClickListner implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //seeing what was clicked in the list view and then starting that activity
            String itemSelected = (String) parent.getItemAtPosition(position);

            Intent newIntent = null;

            switch(itemSelected)
            {
                // when the show map is clicked it calls the map intent and passes in the coordinates to be shown
                case "Show Map":
                    newIntent = new Intent(MenuActivity.this, MapsActivity.class);
                    LatLng coordinates = new LatLng(latitude,longitude);
                    newIntent.putExtra("coordinates", coordinates);
                    break;

                //shows the activity for the user to add a new activity to teh databasae
                case "Add an Activity":
                    newIntent = new Intent(MenuActivity.this, AddInfoActivity.class);
                    newIntent.putExtra("edit", false);
                    break;

                //calls the activity to show the user all the activities in the database
                case "View All Activities":
                    newIntent = new Intent(MenuActivity.this, ViewAllActivites.class);
                    break;

                default:
                    break;
            }
            if(newIntent != null)
                startActivity(newIntent);
        }
    }
}
