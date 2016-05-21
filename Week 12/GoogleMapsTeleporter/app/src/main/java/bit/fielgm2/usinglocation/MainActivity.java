package bit.fielgm2.usinglocation;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    private GoogleMap map;
    private LatLng latLng;
    private SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeLongAndLat();
        Button teleport = (Button) findViewById(R.id.teleportBtn);
        teleport.setOnClickListener(new RandLongLatClickHandler());

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new ShowMap());
    }

    //gets longitude and latitude
    public void makeLongAndLat() {
        Random random = new Random();

        double longitude = -180.0 + random.nextDouble() * 360.0;
        double latitude = -90.0 + random.nextDouble() * 180.0;

        latLng = new LatLng(latitude,longitude);
    }


    public class RandLongLatClickHandler implements View.OnClickListener {
        //gets a city when button clicked
        @Override
        public void onClick(View v) {
            makeLongAndLat();

            mapFragment.getMapAsync(new ShowMap());
        }
    }


    public class ShowMap implements OnMapReadyCallback
    {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            map = googleMap;

            map.addMarker(new MarkerOptions().position(latLng));

            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

}
