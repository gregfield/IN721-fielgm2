package bit.fielgm2.firstactivityswitchingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        Button changeActivity = (Button) findViewById(R.id.changeActivitybtn);
        changeActivity.setOnClickListener(new ChangeActivity());
    }

    public class ChangeActivity implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent activityB = new Intent(ActivityA.this, ActivityB.class);
            startActivity(activityB);
        }
    }
}
