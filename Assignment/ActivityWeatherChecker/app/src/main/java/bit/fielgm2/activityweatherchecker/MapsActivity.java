package bit.fielgm2.activityweatherchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity {

    private final int MAPZOOMLEVEL = 15;

    private GoogleMap map;
    private LatLng currentLatLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //get coordinates passed in from previous activity
        Bundle coords = getIntent().getExtras();
        currentLatLang = (LatLng) coords.get("coordinates");

        //makes the fragment and calls the map inner class
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new MapCallBackClass());
    }

    //===================
    //map innner class
    //===================
    public class MapCallBackClass implements OnMapReadyCallback
    {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            //gets the map
            map = googleMap;

            map.addMarker(new MarkerOptions().position(currentLatLang));

            //takes the camera to the correct coordinates then zooms into an appropriate level
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLatLang).zoom(MAPZOOMLEVEL).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
