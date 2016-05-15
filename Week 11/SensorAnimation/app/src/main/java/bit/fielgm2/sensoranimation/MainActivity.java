package bit.fielgm2.sensoranimation;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private AccelerometerSensor accelerometer;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView);

        //gets sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //inner class for acceleromter sensor
        accelerometer = new AccelerometerSensor();
        //gets accelerometer from sensor manager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    protected void onPause()
    {
        //turns accelerometer off
        super.onPause();
        sensorManager.unregisterListener(accelerometer);
    }
    @Override
    protected void onResume()
    {
        //turns accelerometer on
        super.onResume();
        sensorManager.registerListener(accelerometer, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    private class AccelerometerSensor implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event) {

            //getting layout of imageview
            RelativeLayout.LayoutParams imageViewParameters = (RelativeLayout.LayoutParams) image.getLayoutParams();

            //getting screen bounds
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            //deciding where to move
            if(event.values[0] > 0)
            {
                //go right
                imageViewParameters.leftMargin -= 50;

            }else if(event.values[0] < 0)
            {
                //go left
                imageViewParameters.leftMargin += 50;
            }

            if(event.values[1] > 0)
            {
                //go down
                imageViewParameters.topMargin += 50;

            }else if(event.values[1] < 0)
            {
                //go up
                imageViewParameters.topMargin -= 50;
            }

            image.setLayoutParams(imageViewParameters);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
