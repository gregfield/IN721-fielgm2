package bit.fielgm2.jsonfile;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    ArrayList<Events> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsList = new ArrayList<Events>();
        addEventsToEventList();

        Button fillListBtn = (Button) findViewById(R.id.fillistBtn);
        fillListBtn.setOnClickListener(new fillListView());

    }

    public String getJSONInput()
    {
        String JSONInput ="";
        try {
            String assetFileName = "dunedin_events.json";

            AssetManager am = getAssets();
            InputStream inputStream = am.open(assetFileName);

            int fileSizeInBytes = inputStream.available();
            byte[] JSONBuffer = new byte[fileSizeInBytes];

            inputStream.read(JSONBuffer);
            inputStream.close();

            JSONInput = new String(JSONBuffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return JSONInput;
    }

    public void addEventsToEventList()
    {
        String JSONInput = getJSONInput();

        try {
            JSONObject eventData = new JSONObject(JSONInput);
            JSONObject eventsObject = eventData.getJSONObject("events");
            JSONArray eventArray = eventsObject.getJSONArray("event");

            int numOfEvents = eventArray.length();

            for(int i = 0; i < numOfEvents; i++)
            {
                JSONObject eventObject = eventArray.getJSONObject(i);

                String title = eventObject.getString("title");
                String description = eventObject.getString("description");

                eventsList.add(new Events(title, description));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class fillListView implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            ArrayList<String> eventTitles = new ArrayList<String>();
            //java foreach equivalent
            for(Events eventTitle : eventsList)
            {
                eventTitles.add(eventTitle.getEventTitle());
            }

            ListView listView = (ListView) findViewById(R.id.listView);

            ArrayAdapter<String> eventsAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, eventTitles);

            listView.setAdapter(eventsAdapter);
        }
    }
}
