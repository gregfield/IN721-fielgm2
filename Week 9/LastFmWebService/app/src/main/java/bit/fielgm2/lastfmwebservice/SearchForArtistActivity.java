package bit.fielgm2.lastfmwebservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchForArtistActivity extends AppCompatActivity {

    ArrayList<Artists> searchedArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_artist);

        searchedArtists = new ArrayList<Artists>();

        Button search = (Button) findViewById(R.id.searchArtistBtn);
        search.setOnClickListener(new SearchArtistsOnClick());
    }

    public class GetSearchedArtists extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params)
        {
            String searchString = params[0];
            String JSONString = "";

            try {
                String url = "http://ws.audioscrobbler.com/2.0/?" +
                        "method=artist.getSimilar&" + "artist=" + searchString +
                        "&api_key=58384a2141a4b9737eacb9d0989b8a8c&" +
                        "limit=10&format=json";

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
                    Toast.makeText(SearchForArtistActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String fetchedString) {
            addArtistsToList(fetchedString);
        }
    }
    public void addArtistsToList(String input)
    {
        searchedArtists.clear();
        String JSONInput = input;

        try {
            JSONObject topArtistsData = new JSONObject(JSONInput);
            JSONObject artistObject = topArtistsData.getJSONObject("similarartists");
            JSONArray artistArray = artistObject.getJSONArray("artist");

            int numOfEvents = artistArray.length();

            for(int i = 0; i < numOfEvents; i++)
            {
                JSONObject eventObject = artistArray.getJSONObject(i);

                String name = eventObject.getString("name");

                searchedArtists.add(new Artists(name));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        populateListView();
    }
    private class SearchArtistsOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText inputText = (EditText) findViewById(R.id.editText);
            String input = inputText.getText().toString();
            GetSearchedArtists searchForArtist = new GetSearchedArtists();
            searchForArtist.execute(input);
            inputText.setText("");
        }
    }
    public void populateListView() {
        // for no custom array adapter
        ArrayList<String> artists = new ArrayList<String>();
        for(Artists artist : searchedArtists)
        {
            artists.add(artist.getName());
        }
        ListView listView = (ListView) findViewById(R.id.searchedListView);

        ArrayAdapter adapter = new ArrayAdapter(SearchForArtistActivity.this, R.layout.support_simple_spinner_dropdown_item, artists);
        listView.setAdapter(adapter);
    }

}
