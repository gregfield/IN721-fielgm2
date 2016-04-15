package bit.fielgm2.lastfmartistimageapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Artists artist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetTopArtists getImage = new GetTopArtists();
        getImage.execute();

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new ImagebtnOnClick());
    }

    public class GetTopArtists extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params)
        {
            String JSONString = "";

            try {
                String url = "http://ws.audioscrobbler.com/2.0/?" +
                        "method=chart.gettopartists&" +
                        "api_key=58384a2141a4b9737eacb9d0989b8a8c&" +
                        "limit=20&format=json";

                URL URLObject = new URL(url);

                HttpURLConnection lastFmConnection = (HttpURLConnection) URLObject.openConnection();
                lastFmConnection.connect();

                int responseCode = lastFmConnection.getResponseCode();

                InputStream inputStream = lastFmConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String responseString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((responseString = bufferedReader.readLine()) != null) {
                    stringBuilder = stringBuilder.append(responseString);
                }
                JSONString = stringBuilder.toString();

            }catch (Exception e){
                e.printStackTrace();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String fetchedString) {
            TextView as = (TextView) findViewById(R.id.textView);
            as.setText(fetchedString);
            getArtistImage(fetchedString);
        }
    }

    public void getArtistImage(String input)
    {
        String JSONInput = input;

        try {
            JSONObject topArtistsData = new JSONObject(JSONInput);
            JSONObject artistObject = topArtistsData.getJSONObject("artists");
            JSONArray artistArray = artistObject.getJSONArray("artist");

            JSONObject eventObject = artistArray.getJSONObject(0);

            String name = eventObject.getString("name");
            int listenerCount = eventObject.getInt("listeners");

            JSONArray imageArray = eventObject.getJSONArray("image");
            JSONObject imageObject = imageArray.getJSONObject(0);

            String image = imageObject.getString("#text");
            artist = new Artists(name, image, listenerCount);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class GetImage extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params)
        {
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.judge);;
            try {
                String url = params[0];

                URL URLObject = new URL(url);

                HttpURLConnection imageConnection = (HttpURLConnection) URLObject.openConnection();
                imageConnection.connect();

                int responseCode = imageConnection.getResponseCode();
                if(responseCode == 200) {

                    InputStream inputStream = imageConnection.getInputStream();

                    image = BitmapFactory.decodeStream(inputStream);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap fetchedImage) {
            ImageView image = (ImageView) findViewById(R.id.imageView);
            if (image != null) {
                image.setImageBitmap(fetchedImage);
            }
        }
    }

    protected class ImagebtnOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            GetImage getImage = new GetImage();
            getImage.execute(artist.getImageUrl());
        }
    }
}
