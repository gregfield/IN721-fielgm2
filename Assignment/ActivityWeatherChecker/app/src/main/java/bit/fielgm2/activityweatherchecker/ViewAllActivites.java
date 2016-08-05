package bit.fielgm2.activityweatherchecker;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.ExecutionException;

public class ViewAllActivites extends AppCompatActivity
{
    //enum for the options the user selected in the options dialog
    public enum Options {weather, delete, map, edit}

    //fields fro class
    private OptionsFragment optionsFragment;
    private SQLiteDatabase locationsDatabase;
    private DatabaseRecord[] records;
    private int activityPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_activities);

        //opens the database for use
        locationsDatabase = openOrCreateDatabase("locationsDatabase", MODE_PRIVATE, null);

        //gets the records from the database
        getRecordFromDb();
        //puts the records in a list view
        writeRecordsToList();
    }

    //gets all the records from the database so that they can be displayed
    public void getRecordFromDb()
    {
        String getAllActivitesQuery = "SELECT * FROM tblLocations ORDER BY date('date')";
        Cursor recordSet = locationsDatabase.rawQuery(getAllActivitesQuery, null);

        int recordCount = recordSet.getCount();

        records = new DatabaseRecord[recordCount];

        int activityIdIndex = recordSet.getColumnIndex("loactionsID");
        int activityNameIndex = recordSet.getColumnIndex("activityName");
        int dateIndex = recordSet.getColumnIndex("date");
        int latitudeIndex = recordSet.getColumnIndex("latitude");
        int longitudeIndex = recordSet.getColumnIndex("longitude");
        int cityNameIndex = recordSet.getColumnIndex("cityName");

        recordSet.moveToFirst();

        for (int i = 0; i < records.length; i++)
        {
            int activityId = recordSet.getInt(activityIdIndex);
            String activityName = recordSet.getString(activityNameIndex);
            String date = recordSet.getString(dateIndex);
            double latitude = recordSet.getDouble(latitudeIndex);
            double longitude = recordSet.getDouble(longitudeIndex);
            String cityName = recordSet.getString(cityNameIndex);

            records[i] = new DatabaseRecord(activityId, activityName, latitude, longitude, cityName, date);

            recordSet.moveToNext();
        }
    }

    //puts all the records into a list view for the user to see
    public void writeRecordsToList()
    {
        DBRecordArrayAdapter adapter = new DBRecordArrayAdapter(ViewAllActivites.this, R.layout.db_records_list_view, records);

        ListView listView = (ListView) findViewById(R.id.activityListListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new listViewClickListener());
    }

    //when the user has chosen an option for what to do to the selected activity this is called
    public void optionSelected(Options choice)
    {
        //closes the options fragment
        optionsFragment.dismiss();

        switch (choice)
        {
            //if the show weather was selected it calls the method to show the weather
            case weather:
                showWeather();
                break;
            //if delete was selected it deletes record from db
            case delete:
                deleteEntry();
                break;
            //if map selected it shows the location of the activity in a map
            case map:
                showMap();
                break;
            //if edit is call it brings up the addinfoActivity for editing the activity
            case edit:
                editEntry();
                break;
        }

    }

    //gets the weather informaiton for the selected activity then calls the activity to display it
    public void showWeather()
    {
        ProgressDialog showProgress = ProgressDialog.show(ViewAllActivites.this, "Progress", "Getting Weather Information for current location", true);

        AsyncCalls.GetWeatherCity weather = new AsyncCalls.GetWeatherCity();
        String JSONString = "";
        try {
            JSONString = weather.execute(records[activityPosition].getCityName()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        showProgress.dismiss();

        Intent displayIntent = new Intent(ViewAllActivites.this, DisplayWeatherActivty.class);
        displayIntent.putExtra("jsonString", JSONString);
        displayIntent.putExtra("location", records[activityPosition].getCityName());
        startActivity(displayIntent);
    }

    //deletes the selected database entry
    public void deleteEntry()
    {
        String deleteQuery = "DELETE FROM tblLocations WHERE loactionsID='" + records[activityPosition].getActivityId() + "';";

        locationsDatabase.execSQL(deleteQuery);
        Toast.makeText(ViewAllActivites.this, "Activity Deleted", Toast.LENGTH_SHORT).show();
        getRecordFromDb();
        writeRecordsToList();
    }

    //shows the location of teh activity selected on the map
    public void showMap()
    {
        Intent mapIntent = new Intent(ViewAllActivites.this, MapsActivity.class);
        LatLng coordinates = new LatLng(records[activityPosition].getLatitude(),records[activityPosition].getLongitude());
        mapIntent.putExtra("coordinates", coordinates);
        startActivity(mapIntent);
    }

    //shows the activty to edit teh selected database entry
    public void editEntry()
    {
        Intent editInfo = new Intent(ViewAllActivites.this, AddInfoActivity.class);
        editInfo.putExtra("edit", true);
        editInfo.putExtra("activity", records[activityPosition].getActivityId());
        startActivity(editInfo);
    }

    //=======================================
    //Inner Classes
    //=======================================

    //custom array adapter to show teh database entrys in teh list view
    public class DBRecordArrayAdapter extends ArrayAdapter<DatabaseRecord>
    {
        public DBRecordArrayAdapter(Context context, int resource, DatabaseRecord[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container)
        {
            LayoutInflater inflater = LayoutInflater.from(ViewAllActivites.this);

            View customView = inflater.inflate(R.layout.db_records_list_view, container, false);

            TextView name = (TextView) customView.findViewById(R.id.dbListTxt1);
            TextView date = (TextView) customView.findViewById(R.id.dbListTxt2);

            DatabaseRecord currentRecord = getItem(position);

            name.setText(currentRecord.getActivityName());
            date.setText(currentRecord.getDate());

            return  customView;
        }
    }

    //onclick listener for the list view and shosws the options fragment when used
    public class listViewClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //seeing what was clicked in the list view and then starting that activity
            activityPosition = position;

            optionsFragment = new OptionsFragment();
            FragmentManager fm = getFragmentManager();
            optionsFragment.show(fm, "options");
        }
    }
}
