package bit.fielgm2.musicschoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button changeActivity = (Button) findViewById(R.id.nextActivityBtn);
        changeActivity.setOnClickListener(new goToSelectInstrument());
    }

    public class goToSelectInstrument implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent chooseInstruIntent = new Intent(startActivity.this, MainActivity.class);
            startActivity(chooseInstruIntent);
        }
    }
}
