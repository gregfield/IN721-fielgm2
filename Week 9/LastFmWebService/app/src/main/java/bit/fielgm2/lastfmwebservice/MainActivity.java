package bit.fielgm2.lastfmwebservice;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public class getTopArtists extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params)
        {
            String JSONString = "";

            try {
            }catch (Exception e){
            }

            return JSONString;
        }
    }
}

