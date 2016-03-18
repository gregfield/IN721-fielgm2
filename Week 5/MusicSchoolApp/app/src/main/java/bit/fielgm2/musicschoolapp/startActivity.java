package bit.fielgm2.musicschoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.logging.Handler;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.startActivityLayout);
        relLayout.setOnClickListener(new nextActivity());
    }

    public class nextActivity implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent chooseInstrumentIntent = new Intent(startActivity.this, MainActivity.class);
            startActivity(chooseInstrumentIntent);
        }
    }
}
