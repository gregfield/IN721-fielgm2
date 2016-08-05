package bit.fielgm2.activityweatherchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class DisplayWeatherActivty extends AppCompatActivity {

    //arraylist for the forecast
    private ArrayList<Weather> forecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather_activty);

        //gets the forecast
        getForecast();

        //loading spinner
        String[] spinnerArray = getDaysForSpinner();
        Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spinnerArray);
        daySpinner.setAdapter(spinnerAdapter);
        daySpinner.setOnItemSelectedListener(new spinnerSelected());

        //getting json
        String JSONString = getIntent().getExtras().getString("jsonString");
        Weather activityWeather = JSONProcessors.weatherJSONParser(JSONString);
        //displaying the weather information
        displayInfo(activityWeather);

    }

    //gets the forecast for the next 10 days and puts it in an arraylist
    public void getForecast()
    {
        //get the location that was passed into activity
        String loaction = getIntent().getExtras().getString("location");
        //calls the relevant async call
        AsyncCalls.GetWeatherNext10Days getForecast = new AsyncCalls.GetWeatherNext10Days();
        String jsonString = "";
        try
        {
            jsonString = getForecast.execute(loaction).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //converts the json and adds it to the forecast list
        forecastList = JSONProcessors.forecastJSONParser(jsonString);
    }

    //displays the weather information in the relevant textviewws
    public void displayInfo(Weather weather)
    {
        TextView tempTxtView = (TextView) findViewById(R.id.tableCurrTemp1);
        TextView minTempTxtView = (TextView) findViewById(R.id.tableMinTemp1);
        TextView maxTempTxtView = (TextView) findViewById(R.id.tableMaxTemp1);
        TextView humidityTxtView = (TextView) findViewById(R.id.tableHumidity1);
        TextView pressureTxtView = (TextView) findViewById(R.id.tablePressure1);

        tempTxtView.setText(String.valueOf(weather.getCurrentTemp()));
        minTempTxtView.setText(String.valueOf(weather.getMinTemp()));
        maxTempTxtView.setText(String.valueOf(weather.getMaxTemp()));
        humidityTxtView.setText(String.valueOf(weather.getHumidity()));
        pressureTxtView.setText(String.valueOf(weather.getPressure()));
    }

    //gets the correct set of 10 days for the spinner depending on what the current day is
    public String[] getDaysForSpinner()
    {
        int numberOfDays = 10;

        String[] spinnerArray = new String[numberOfDays];

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day)
        {
            case Calendar.SUNDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday"};
                break;
            case Calendar.MONDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"};
                break;
            case Calendar.TUESDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Thursday", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
                break;
            case Calendar.WEDNESDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Friday", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
                break;
            case Calendar.THURSDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                break;
            case Calendar.FRIDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                break;
            case Calendar.SATURDAY:
                spinnerArray = new String[]{"Today", "Tomorrow", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Monday"};
                break;
        }
        return spinnerArray;
    }

    //=======================================
    //Inner Class
    //=======================================

    //calls the displayinfo method to show the information for the day selected
    public class spinnerSelected implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            TextView dayTxtView = (TextView) findViewById(R.id.dayTxtView);
            dayTxtView.setText("Day");
            displayInfo(forecastList.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
