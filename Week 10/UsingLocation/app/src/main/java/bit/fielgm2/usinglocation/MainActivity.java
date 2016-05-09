package bit.fielgm2.usinglocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        Button randLonLat = (Button) findViewById(R.id.ranLongLatBtn);
        randLonLat.setOnClickListener(new RandLongLatClickHandler());
    }

    public class RandLongLatClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            LongandLat();
        }
    }
    public void LongandLat()
    {
        double longitude = -180.0 + random.nextDouble() * 360.0;
        double latitude = -90.0 + random.nextDouble() * 180.0;
        longitude = Math.round(longitude * 1000) / 1000;
        latitude = Math.round(latitude * 1000) / 1000;
        TextView textView = (TextView) findViewById(R.id.longlatTxtView);
        textView.setText(longitude + "  " + latitude);

    }

}
