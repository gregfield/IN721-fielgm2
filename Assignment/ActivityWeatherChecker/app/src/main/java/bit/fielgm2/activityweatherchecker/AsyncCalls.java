package bit.fielgm2.activityweatherchecker;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Greg on 3/06/2016.
 */
public class AsyncCalls
{
    private static String OpenWeatherAPIKEY = "3c8f7419fdea990546c5dc583644001a";

    //get the weather info
    public static class GetWeatherFromLongLat extends AsyncTask<LatLng,Void,String> {

        //goes to Open Weather Map and sends away the longitude and latitude
        //which returns JSON which get converted to a string which gets returned
        @Override
        protected String doInBackground(LatLng... params) {
            String JSONString = "";
            try
            {
                LatLng coords = params[0];
                double latitude = coords.latitude;
                double longitude = coords.longitude;

                String url = "http://api.openweathermap.org/data/2.5/weather?APPID=" + OpenWeatherAPIKEY
                        + "&units=metric&lat=" + latitude + "&lon=" + longitude;


                URL URLObject = new URL(url);

                HttpURLConnection openWeatherConnection = (HttpURLConnection) URLObject.openConnection();
                openWeatherConnection.connect();

                int responseCode = openWeatherConnection.getResponseCode();
                if (responseCode == 200)
                {
                    InputStream inputStream = openWeatherConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null)
                    {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();
                }
                else
                {
                    JSONString = "Could Not Connect";
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return JSONString;
        }
    }

    public static class GetWeatherCity extends AsyncTask<String,Void,String> {

        //goes to Open Weather Map and sends away the city name
        //which returns JSON which get converted to a string which gets returned
        @Override
        protected String doInBackground(String... params) {
            String JSONString = "";
            String OpenWeatherAPIKEY = "3c8f7419fdea990546c5dc583644001a";
            try
            {
                String cityName = params[0];

                String url = "http://api.openweathermap.org/data/2.5/weather?APPID=" + OpenWeatherAPIKEY
                        + "&units=metric&q=" + cityName;

                URL URLObject = new URL(url);

                HttpURLConnection openWeatherConnection = (HttpURLConnection) URLObject.openConnection();
                openWeatherConnection.connect();

                int responseCode = openWeatherConnection.getResponseCode();
                if (responseCode == 200)
                {
                    InputStream inputStream = openWeatherConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null)
                    {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();
                }
                else
                {
                    JSONString = "Could Not Connect";
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return JSONString;
        }
    }

    public static class GetWeatherNext10Days extends AsyncTask<String,Void,String> {

        //goes to Open Weather Map and sends away city name and gets the forecast for the next 10 days
        //which returns JSON which get converted to a string which gets returned
        @Override
        protected String doInBackground(String... params) {
            String JSONString = "";
            String OpenWeatherAPIKEY = "3c8f7419fdea990546c5dc583644001a";
            try
            {
                String cityName = params[0];

                String url = "http://api.openweathermap.org/data/2.5/forecast/daily?APPID=" + OpenWeatherAPIKEY
                        + "&units=metric&cnt=10&q=" + cityName;


                URL URLObject = new URL(url);

                HttpURLConnection openWeatherConnection = (HttpURLConnection) URLObject.openConnection();
                openWeatherConnection.connect();

                int responseCode = openWeatherConnection.getResponseCode();
                if (responseCode == 200)
                {
                    InputStream inputStream = openWeatherConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null)
                    {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();
                }
                else
                {
                    JSONString = "Could Not Connect";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return JSONString;
        }
    }

    public static class GetLatLongFromAddress extends AsyncTask<String,Void,String> {

        //uses google geocoding to get the latitude and longitude
        //for the city that the user entered
        @Override
        protected String doInBackground(String... params) {
            String JSONString = "";

            try
            {
                String cityName = params[0];

                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName;


                URL URLObject = new URL(url);

                HttpURLConnection openWeatherConnection = (HttpURLConnection) URLObject.openConnection();
                openWeatherConnection.connect();

                int responseCode = openWeatherConnection.getResponseCode();
                if (responseCode == 200)
                {
                    InputStream inputStream = openWeatherConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null)
                    {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();
                }
                else
                {
                    JSONString = "Could Not Connect";
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return JSONString;
        }
    }
}
