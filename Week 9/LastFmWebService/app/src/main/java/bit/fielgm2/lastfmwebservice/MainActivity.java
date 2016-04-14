package bit.fielgm2.lastfmwebservice;

import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        Button fillListBtn = (Button) findViewById(R.id.topArtistsBtn);
        fillListBtn.setOnClickListener(new FillList());
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
        ArrayList<String> artists = new ArrayList<String>();
        for(Artists artist : topArtists)
        {
            artists.add(artist.toString());
        }
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, artists);
        listView.setAdapter(adapter);
    }

    private class FillList implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            populateListView();
        }
    }
}

