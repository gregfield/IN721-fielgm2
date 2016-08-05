package bit.fielgm2.activityweatherchecker;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 2/06/2016.
 */
public class JSONProcessors
{

    //gets the relevant information out of the JSON string
    //when it is just the current weather
    public static Weather weatherJSONParser(String JSONString)
    {
        Weather currentWeather = new Weather();

        try
        {
            JSONObject object = new JSONObject(JSONString);
            JSONObject main = object.getJSONObject("main");

            currentWeather.setCurrentTemp(main.getDouble("temp"));
            currentWeather.setMinTemp(main.getDouble("temp_min"));
            currentWeather.setMaxTemp(main.getDouble("temp_max"));
            currentWeather.setHumidity(main.getDouble("humidity"));
            currentWeather.setPressure(main.getDouble("pressure"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return currentWeather;
    }

    //gets the information out of the string for
    //the forecastt for next 10 days
    public static ArrayList<Weather> forecastJSONParser(String JSONString)
    {
        Log.d("json", JSONString);
        ArrayList<Weather> forecast = new ArrayList<Weather>();

        try
        {
            JSONObject object = new JSONObject(JSONString);
            JSONArray listArray = object.getJSONArray("list");

            for(int i = 0; i < listArray.length(); i++)
            {
                JSONObject main = listArray.getJSONObject(i);
                JSONObject temp = main.getJSONObject("temp");
                Weather weather = new Weather();
                weather.setCurrentTemp(temp.getDouble("day"));
                weather.setMinTemp(temp.getDouble("min"));
                weather.setMaxTemp(temp.getDouble("max"));
                weather.setHumidity(main.getDouble("humidity"));
                weather.setPressure(main.getDouble("pressure"));
                forecast.add(weather);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return forecast;
    }

    //gets latitude and longitude out of json for
    //when city is entered for a actvity
    public static LatLng cityLatLang(String JSONString)
    {
        LatLng coordinates = null;

        JSONObject object = null;
        try
        {
            object = new JSONObject(JSONString);

            JSONArray resultsArray = object.getJSONArray("results");


            JSONObject info = resultsArray.getJSONObject(1);
            JSONObject geometry = info.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");

            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            coordinates = new LatLng(lat, lng);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return coordinates;
    }
}
