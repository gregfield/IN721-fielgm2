package bit.fielgm2.firstactivityswitchingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        TextView activityHeading = (TextView) findViewById(R.id.activityHeading);
        activityHeading.setText(R.string.acivityB);
        Button changeActivity = (Button) findViewById(R.id.changeActivitybtn);
        changeActivity.setOnClickListener(new ChangeActivity());
    }

    public class ChangeActivity implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent activityC = new Intent(ActivityB.this, ActivityC.class);
            startActivity(activityC);
        }
    }
}
