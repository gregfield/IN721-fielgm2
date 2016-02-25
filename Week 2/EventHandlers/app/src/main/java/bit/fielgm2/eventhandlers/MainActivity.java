package bit.fielgm2.eventhandlers;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLongAndShort = (Button) findViewById(R.id.longAndShort);

        buttonLongAndShort.setOnClickListener(new ButtonChangeDisplayClickHandler());
        buttonLongAndShort.setOnLongClickListener(new ButtonChangeDisplayClickHandlerLong());

    }

    public class ButtonChangeDisplayClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Toast.makeText(MainActivity.this,"Short Click", Toast.LENGTH_LONG).show();
        }
    }
    public class ButtonChangeDisplayClickHandlerLong implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v)
        {
            Toast.makeText(MainActivity.this,"Long Click", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
