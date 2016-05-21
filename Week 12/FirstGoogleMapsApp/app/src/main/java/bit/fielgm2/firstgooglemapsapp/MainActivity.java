package bit.fielgm2.firstgooglemapsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private LatLng DunedinLatLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DunedinLatLang = new LatLng(45.8788,170.5028);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new MapCallBackClass());
    }

    public class MapCallBackClass implements OnMapReadyCallback
    {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;

            mMap.addMarker(new MarkerOptions().position(DunedinLatLang).title("Dunedin"));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(DunedinLatLang));
        }
    }
}
