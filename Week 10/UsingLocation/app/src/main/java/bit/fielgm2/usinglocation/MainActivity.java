package bit.fielgm2.usinglocation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    double longitude;
    double latitude;
    String city;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        Button randLonLat = (Button) findViewById(R.id.ranLongLatBtn);
        randLonLat.setOnClickListener(new RandLongLatClickHandler());
    }

    public class RandLongLatClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            makeLongAndLat();
            GeoPluginNearestCity nearestCity = new GeoPluginNearestCity();
            nearestCity.execute();
        }
    }

    public void makeLongAndLat()
    {
        longitude = -180.0 + random.nextDouble() * 360.0;
        latitude = -90.0 + random.nextDouble() * 180.0;
    }
    public void displayInfo()
    {
        TextView longandLat = (TextView) findViewById(R.id.longlatTxtView);
        TextView nearestCity = (TextView) findViewById(R.id.nearestCitytxtView);

        longitude = Math.round(longitude * 1000) / 1000;
        latitude = Math.round(latitude * 1000) / 1000;

        longandLat.setText(longitude + "  " + latitude);
        nearestCity.setText(city);
    }

    public void nearestCityJson(String JSONString){
        JSONObject JSON = null;
        try {

            if(JSONString.equals("[[]]")){
                city = "No city found.";
            }
            else{
                JSON = new JSONObject(JSONString);
                String name = JSON.getString("geoplugin_place");
                String countryCode = JSON.getString("geoplugin_countryCode");

                city = name + ", " + countryCode;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class GeoPluginNearestCity extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params)
        {
            String JSONString = "";

            try {
                String url = "http://www.geoplugin.net/extras/location.gp?"+
                        "lat="+latitude+"&long="+longitude+"&format=json";;

                URL URLObject = new URL(url);

                HttpURLConnection geoPluginConnection = (HttpURLConnection) URLObject.openConnection();
                geoPluginConnection.connect();

                int responseCode = geoPluginConnection.getResponseCode();
                if(responseCode == 200) {

                    InputStream inputStream = geoPluginConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null) {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String fetchedString) {
            nearestCityJson(fetchedString);
            displayInfo();
        }
    }
}
