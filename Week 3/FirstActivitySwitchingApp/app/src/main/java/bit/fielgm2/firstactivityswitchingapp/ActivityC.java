package bit.fielgm2.firstactivityswitchingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class ActivityC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        TextView activityHeading = (TextView) findViewById(R.id.activityHeading);
        activityHeading.setText(R.string.activityC);

        Button changeActivity = (Button) findViewById(R.id.changeActivitybtn);
        changeActivity.setOnClickListener(new ChangeActivity());
    }

    public class ChangeActivity implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Uri goSeeMickey = Uri.parse("http://www.disney.com");
            Intent mickey = new Intent(Intent.ACTION_VIEW, goSeeMickey);
            startActivity(mickey);
        }
    }
}
