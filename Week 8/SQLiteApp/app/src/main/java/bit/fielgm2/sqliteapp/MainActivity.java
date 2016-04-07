package bit.fielgm2.sqliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase citiesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citiesDatabase = openOrCreateDatabase("citiesDatabase", MODE_PRIVATE, null);

        createTablesQuery();
        insertData();
        fillSpinner(returnCountries());

        Button search = (Button) findViewById(R.id.fillListBtn);
        search.setOnClickListener(new DisplayList());

    }

    //creates the table
    public void createTablesQuery() {

        String dropQuery = "DROP TABLE IF EXISTS tblCities;";
        citiesDatabase.execSQL(dropQuery);

        String createCitiesTableQuery = "CREATE TABLE IF NOT EXISTS tblCities(" +
                "cityID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cityName TEXT NOT NULL, " +
                "countryName TEXT NOT NULL);";
        citiesDatabase.execSQL(createCitiesTableQuery);
    }
    //inserts data into the table
    public void insertData()
    {
        String[] cityNames = {"Amsterdam", "Berlin", "Cairo", "Dunedin"};
        String[] countries = {"The Netherlands", "Germany", "Egypt", "New Zealand"};

        for (int i =0; i < cityNames.length; i++)
        {
            citiesDatabase.execSQL("INSERT INTO tblCities VALUES(null, '" + cityNames[i] + "', '" + countries[i] + "')");
        }
    }
    //returns the cities from database in a string array
    public String[] returnCities(String country)
    {
        String selectQuery = "SELECT * FROM tblCities WHERE countryName LIKE '" + country + "'";
        Cursor recordSet = citiesDatabase.rawQuery(selectQuery, null);

        int recordCount = recordSet.getCount();
        String[] displayStringArray = new String[recordCount];

        int cityNameIndex = recordSet.getColumnIndex("cityName");
        int countryNameIndex = recordSet.getColumnIndex("countryName");

        recordSet.moveToFirst();

        for (int r=0; r < recordCount; r++)
        {
            String cityName = recordSet.getString(cityNameIndex);
            String countryName = recordSet.getString(countryNameIndex);
            displayStringArray[r] = cityName + ", " + countryName;

            recordSet.moveToNext();
        }

        return displayStringArray;
    }

    //return countries
    public ArrayList<String> returnCountries()
    {
        String selectQuery = "SELECT DISTINCT(countryName) FROM tblCities;";
        Cursor recordSet = citiesDatabase.rawQuery(selectQuery,null);

        int count = recordSet.getCount();
        int countryNameIndex = recordSet.getColumnIndex("countryName");
        recordSet.moveToFirst();

        ArrayList<String> countries = new ArrayList<String>();

        for (int r = 0; r < count; r++){
            countries.add(recordSet.getString(countryNameIndex));
            recordSet.moveToNext();
        }

        return countries;
    }

    public class DisplayList implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            fillList();
        }
    }

    public void fillSpinner(ArrayList<String> countries)
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, countries);
        spinner.setAdapter(countriesAdapter);
    }

    public void fillList()
    {
        Spinner selected = (Spinner) findViewById(R.id.spinner);
        ListView listView = (ListView) findViewById(R.id.citiesListView);

        String[] citiesArray = returnCities(selected.getSelectedItem().toString());

        ArrayAdapter<String> citiesArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, citiesArray);

        listView.setAdapter(citiesArrayAdapter);
    }
}
