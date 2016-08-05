package bit.fielgm2.activityweatherchecker;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.concurrent.ExecutionException;

public class AddInfoActivity extends AppCompatActivity {

    //class properties
    private SQLiteDatabase loactionsDatabase;
    private String activityName;
    private String activityDate;
    private String activityLocation;
    private boolean edit;
    private int recordID;
    private DatabaseRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        //opens the database
        loactionsDatabase = openOrCreateDatabase("locationsDatabase", MODE_PRIVATE, null);

        //gets a flag as to whether or not windows has been opened for editing purposes or not
        edit = getIntent().getExtras().getBoolean("edit");
        //if it has it gets the id of the record it is open to edit
        //then calls the FIllForEditActivity method to fill in the fields
        if(edit)
        {
            recordID = getIntent().getExtras().getInt("activity");
            fillForEditActivity();
        }

        //makes the buttons on click listeners
        Button saveBtn = (Button) findViewById(R.id.addToDbBtn);
        Button returnBtn = (Button) findViewById(R.id.returnMenuBtn);

        saveBtn.setOnClickListener(new saveOnClickListner());
        returnBtn.setOnClickListener(new menuOnClickListner());
    }

    //gets the information that has been entered in the form so that it can be put in the database
    public boolean getInfoFromForm()
    {
        EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        TextView date = (TextView) findViewById(R.id.dateTxtView);
        EditText  locationEditTxt = (EditText) findViewById(R.id.locationEditTxt);

        activityName = nameEditTxt.getText().toString();
        activityDate = date.getText().toString();
        activityLocation = locationEditTxt.getText().toString();

        //if there has been a field left empty it sets infoThere to false
        boolean infoThere = true;
        if(nameEditTxt.getText().toString().trim().length() == 0)
        {
            infoThere = false;
        }
        else if(date.getText().toString().trim().length() == 0)
        {
            infoThere = false;
        }
        else if (locationEditTxt.getText().toString().trim().length() == 0)
        {
            infoThere = false;
        }
        return infoThere;
    }

    //adds the information to the database
    public void addToDb()
    {
        //if the infoThere field is true it means all the fields have been filled out
        //so that the information can be entered into the database
        boolean infoThere = getInfoFromForm();
        if(infoThere)
        {
            //gets the coordinates for the city entered so it can be used with the map later
            LatLng coords = getLatLngFromCity();
            double lat = coords.latitude;
            double lng = coords.longitude;

            //it has been opened for editing it runs the update query
            if(edit)
            {
                String query = "UPDATE tblLocations SET activityName = '" + record.getActivityName() +
                        "',date = '" + record.getDate() + "', latitude = " + lat + ", longitude = " + lng +
                        ", cityName = '" + record.getCityName() +
                        "' WHERE loactionsID =" + recordID;

                loactionsDatabase.execSQL(query);;
                Toast.makeText(AddInfoActivity.this, "Activity Updated", Toast.LENGTH_SHORT).show();
            }
            else //if it is not for editing it runs an add query
            {
                String inputQuery = "INSERT INTO tblLocations VALUES(null, '"
                        + activityName +
                        "', '" + activityDate +
                        "', " + lat + ", " + lng + ", '" +
                        activityLocation + "')";

                loactionsDatabase.execSQL(inputQuery);
                Toast.makeText(AddInfoActivity.this, "Activity Added", Toast.LENGTH_SHORT).show();
            }
        }
        else // if all the fields are not filled out it pops a toast
        {
            Toast.makeText(AddInfoActivity.this, "Please Fill in all Fields", Toast.LENGTH_SHORT).show();
        }
    }

    //shows the date picker dialog so the user can pick a date
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        FragmentManager fm = getFragmentManager();
        newFragment.show(fm, "datePicker");
    }

    //when the user has picked a dat it displays it in a text view
    public void onReturnDate(int year, int month, int day)
    {
        TextView date = (TextView) findViewById(R.id.dateTxtView);
        date.setText(day + "/" + month + "/" + year);
    }

    //fills the relevant activity information into the correect places for editing
    public void fillForEditActivity()
    {
        EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        TextView date = (TextView) findViewById(R.id.dateTxtView);
        EditText  locationEditTxt = (EditText) findViewById(R.id.locationEditTxt);

        record = getRecordFromDb();

        nameEditTxt.setText(record.getActivityName());
        date.setText(record.getDate());
        locationEditTxt.setText(record.getCityName());
    }

    //gets the record that is to be edited from the database
    public DatabaseRecord getRecordFromDb()
    {
        String getAllActivitesQuery = "SELECT * FROM tblLocations WHERE loactionsID =" + recordID;
        Cursor recordSet = loactionsDatabase.rawQuery(getAllActivitesQuery, null);

        int recordCount = recordSet.getCount();

        DatabaseRecord record;

        int activityIdIndex = recordSet.getColumnIndex("loactionsID");
        int activityNameIndex = recordSet.getColumnIndex("activityName");
        int dateIndex = recordSet.getColumnIndex("date");
        int latitudeIndex = recordSet.getColumnIndex("latitude");
        int longitudeIndex = recordSet.getColumnIndex("longitude");
        int cityNameIndex = recordSet.getColumnIndex("cityName");

        recordSet.moveToFirst();


        int activityId = recordSet.getInt(activityIdIndex);
        String activityName = recordSet.getString(activityNameIndex);
        String date = recordSet.getString(dateIndex);
        double latitude = recordSet.getDouble(latitudeIndex);
        double longitude = recordSet.getDouble(longitudeIndex);
        String cityName = recordSet.getString(cityNameIndex);

        record = new DatabaseRecord(activityId, activityName, latitude, longitude, cityName, date);

        return record;
    }

    //calls the google api call then turns the json into a LatLng so that the user can enter
    //a city name and the program can get the coordinates for the map
    public LatLng getLatLngFromCity()
    {
        AsyncCalls.GetLatLongFromAddress getLatLng = new AsyncCalls.GetLatLongFromAddress();
        String JSONString ="";
        try {
            JSONString = getLatLng.execute(activityLocation).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("json", JSONString);
        return JSONProcessors.cityLatLang(JSONString);
    }

    //=======================================
    //Inner Classes
    //=======================================

    //on click listner for the saving information button
    public class saveOnClickListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            addToDb();
            EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
            TextView date = (TextView) findViewById(R.id.dateTxtView);
            EditText  locationEditTxt = (EditText) findViewById(R.id.locationEditTxt);

            nameEditTxt.setText("");
            date.setText("");
            locationEditTxt.setText("");
        }
    }
    //on click listener for the back to menu button
    public class menuOnClickListner implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    }
}
