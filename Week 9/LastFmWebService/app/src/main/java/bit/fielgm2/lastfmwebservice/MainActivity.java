package bit.fielgm2.lastfmwebservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

    ArrayList<Artists> topArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topArtists = new ArrayList<Artists>();
        GetTopArtists getTopArtists = new GetTopArtists();
        getTopArtists.execute();

        Button searchBtn = (Button) findViewById(R.id.button);
        Button fillListBtn = (Button) findViewById(R.id.topArtistsBtn);
        fillListBtn.setOnClickListener(new FillList());
        searchBtn.setOnClickListener(new SearchButtonOnClick());
    }

    public class GetTopArtists extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params)
        {
            String JSONString = "";

            try {
                String url = "http://ws.audioscrobbler.com/2.0/?" +
                             "method=chart.getTopArtists&" +
                             "api_key=58384a2141a4b9737eacb9d0989b8a8c&" +
                             "limit=20&format=json";

                URL URLObject = new URL(url);

                HttpURLConnection lastFmConnection = (HttpURLConnection) URLObject.openConnection();
                lastFmConnection.connect();

                int responseCode = lastFmConnection.getResponseCode();
                if(responseCode == 200) {

                    InputStream inputStream = lastFmConnection.getInputStream();
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
            addTopArtistsToList(fetchedString);
        }
    }
    public void addTopArtistsToList(String input)
    {
        String JSONInput = input;

        try {
            JSONObject topArtistsData = new JSONObject(JSONInput);
            JSONObject artistObject = topArtistsData.getJSONObject("artists");
            JSONArray artistArray = artistObject.getJSONArray("artist");

            int numOfEvents = artistArray.length();

            for(int i = 0; i < numOfEvents; i++)
            {
                JSONObject eventObject = artistArray.getJSONObject(i);

                String name = eventObject.getString("name");
                int listenerCount = eventObject.getInt("listeners");

                topArtists.add(new Artists(name, listenerCount));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateListView()
    {
        /* for no custom array adapter
        ArrayList<String> artists = new ArrayList<String>();
        for(Artists artist : topArtists)
        {
            artists.add(artist.toString());
        }
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, artists);
        listView.setAdapter(adapter);*/

        //for custom adapter
        ListView listView = (ListView) findViewById(R.id.listView);
        TopArtistsArrayAdapter adapter = new TopArtistsArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, topArtists);
        listView.setAdapter(adapter);
    }

    private class FillList implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            populateListView();
        }
    }

    public class TopArtistsArrayAdapter extends ArrayAdapter
    {
        public TopArtistsArrayAdapter(Context context, int resource, ArrayList<Artists> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

            View customView = inflater.inflate(R.layout.arrayadapter_layout, container, false);

            TextView text  = (TextView) customView.findViewById(R.id.listViewText);
            TextView text2  = (TextView) customView.findViewById(R.id.listViewText2);

            Artists currentArtist = (Artists) getItem(position);
            text.setText(currentArtist.getName());
            text2.setText(String.valueOf(currentArtist.getListnerCount()));

            return customView;
        }
    }

    public class SearchButtonOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

        }
    }

    public class GetImage extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params)
        {
            Bitmap image;

            try {
                String url = params[0];

                URL URLObject = new URL(url);

                HttpURLConnection imageConnection = (HttpURLConnection) URLObject.openConnection();
                imageConnection.connect();

                int responseCode = imageConnection.getResponseCode();
                if(responseCode == 200) {

                    InputStream inputStream = imageConnection.getInputStream();

                    image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
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
            ImageView image = (ImageView) findViewById(R.id.)
        }
    }
}

