package bit.fielgm2.usinglocation;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
        //gets a city when button clicked
        @Override
        public void onClick(View v) {
            GeoPluginNearestCity nearestCity = new GeoPluginNearestCity();
            nearestCity.execute();
        }
    }

    //makes a new longitude and latitude
    public void makeLongAndLat()
    {
        longitude = -180.0 + random.nextDouble() * 360.0;
        latitude = -90.0 + random.nextDouble() * 180.0;
    }

    //displays longitude latitude and city to the screen
    public void displayInfo()
    {
        TextView longandLat = (TextView) findViewById(R.id.longlatTxtView);
        TextView nearestCity = (TextView) findViewById(R.id.nearestCitytxtView);

        longitude = Math.round(longitude * 1000) / 1000;
        latitude = Math.round(latitude * 1000) / 1000;

        longandLat.setText(longitude + "  " + latitude);
        nearestCity.setText(city);
    }

    //gets the nearest city and country code from JSON
    public void nearestCityJson(String JSONString){
        JSONObject JSON = null;
        try {
                JSON = new JSONObject(JSONString);
                String name = JSON.getString("geoplugin_place");
                String countryCode = JSON.getString("geoplugin_countryCode");

                city = name + "," + countryCode;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //gets the info from geoplugin on the internet
    public class GeoPluginNearestCity extends AsyncTask<Void,Void,String> {

        ProgressDialog showProgress;
        //shows a progress dialog when looking for a city
        @Override
        protected void onPreExecute(){
            showProgress = ProgressDialog.show(MainActivity.this,"Progress","Looking For a Location...",true);
        }

        //goes to geoplugin and gets the closest city to the longitude and latitude
        //if there is no close city it keeps making new longitudes and latitudes until
        //it finds a city
        @Override
        protected String doInBackground(Void... params)
        {
            String JSONString = "[[]]";
            while (JSONString.equals("[[]]")) {
                makeLongAndLat();
                try {
                    String url = "http://www.geoplugin.net/extras/location.gp?" +
                            "lat=" + latitude + "&long=" + longitude + "&format=json";
                    ;

                    URL URLObject = new URL(url);

                    HttpURLConnection geoPluginConnection = (HttpURLConnection) URLObject.openConnection();
                    geoPluginConnection.connect();

                    int responseCode = geoPluginConnection.getResponseCode();
                    if (responseCode == 200) {

                        InputStream inputStream = geoPluginConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        String responseString;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((responseString = bufferedReader.readLine()) != null) {
                            stringBuilder = stringBuilder.append(responseString);
                        }
                        JSONString = stringBuilder.toString();
                    } else {
                        Toast.makeText(MainActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return JSONString;
        }

        //closes the progress dialog then calls the methods to get the city and display it
        @Override
        protected void onPostExecute(String fetchedString) {
            showProgress.dismiss();
            nearestCityJson(fetchedString);
            displayInfo();
            GetImageFromFlickr ge = new GetImageFromFlickr();
            ge.execute();
        }
    }

    public class GetImageFromFlickr extends AsyncTask<Void,Void,Bitmap> {

        ProgressDialog showProgress;
        //shows a progress dialog when looking for an image
        @Override
        protected void onPreExecute(){
            showProgress = ProgressDialog.show(MainActivity.this,"Progress","Retrieving Image",true);
        }

        //gets an image from flickr
        @Override
        protected Bitmap doInBackground(Void... params)
        {
            String JSONString = "";
            Bitmap image = null;
            try {
                String flickrUrl = "https://api.flickr.com/services/rest/?" +
                             "&method=flickr.photos.search&" +
                             "api_key=eda41a123d459be0f85276d37290651e" + "&tags=" + city +
                             "&page=1&per_page=1" +
                             "&format=json&nojsoncallback=1";

                URL URLObject = new URL(flickrUrl);

                HttpURLConnection flickrConnection = (HttpURLConnection) URLObject.openConnection();
                flickrConnection.connect();

                int responseCode = flickrConnection.getResponseCode();
                if(responseCode == 200) {

                    InputStream inputStream = flickrConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String responseString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((responseString = bufferedReader.readLine()) != null) {
                        stringBuilder = stringBuilder.append(responseString);
                    }
                    JSONString = stringBuilder.toString();

                    //get image info out of JSON
                    String[] imageInfo = imageJson(JSONString);

                    //
                    //new connection to get image
                    //
                    if(imageInfo!= null) {
                        flickrUrl = "https://farm" + imageInfo[0]
                                + ".staticflickr.com/"
                                + imageInfo[1] + "/"
                                + imageInfo[2] + "_" + imageInfo[3] + ".jpg";
                        URLObject = new URL(flickrUrl);
                        HttpURLConnection imageConnection = (HttpURLConnection) URLObject.openConnection();
                        imageConnection.connect();
                        InputStream imageInputStream = imageConnection.getInputStream();

                        image = BitmapFactory.decodeStream(imageInputStream);
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

                return image;
        }

        //closes the progress dialog then calls the methods to get the city and display it
        @Override
        protected void onPostExecute(Bitmap fetchedImage) {
            showProgress.dismiss();
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            if(fetchedImage != null) {
                imageView.setImageBitmap(fetchedImage);
            }
            else
            {
                imageView.setImageResource(R.drawable.noimageavailable);
            }
        }
    }

    public String[] imageJson(String JSONString)
    {
        String [] imageInfo = new String[4];
        try {
            JSONObject imageDataObject = new JSONObject(JSONString);
            JSONObject images = imageDataObject.getJSONObject("photos");
            int numberImages =images.getInt("total");
            JSONArray photoArray = images.getJSONArray("photo");

            if (numberImages == 0) {
                imageInfo = null;
            } else {
                JSONObject photo = photoArray.getJSONObject(0);

                imageInfo[0] = String.valueOf(photo.getInt("farm"));
                imageInfo[1] = photo.getString("server");
                imageInfo[2] = photo.getString("id");
                imageInfo[3] = photo.getString("secret");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageInfo;
    }
}
