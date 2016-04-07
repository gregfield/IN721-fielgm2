package bit.fielgm2.externalfilessimpleio;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = new ArrayList<String>();

        AssetManager am = getAssets();
        try {
            InputStreamReader is = new InputStreamReader(am.open("city_names.txt"));
            BufferedReader bufferedReader = new BufferedReader(is);

            String newCity;
            while ((newCity = bufferedReader.readLine()) != null)
            {
                cities.add(newCity);
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Button fillList = (Button) findViewById(R.id.fillListBtn);
        fillList.setOnClickListener(new FillListHandler());
    }

    public class FillListHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            fillListView();

        }
    }

    public void fillListView()
    {
        ListView citiesListView = (ListView) findViewById(R.id.citiesListView);
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, cities);

        citiesListView.setAdapter(citiesAdapter);
    }
}