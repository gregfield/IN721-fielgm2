package bit.fielgm2.datapassing_settingsfromanotheractivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //fill text view
        TextView insertText = (TextView) findViewById(R.id.colourtextRed);
        for (int i=0;i<5; i++)
        {
            insertText.append(getString(R.string.ColourOfText));
        }

        //makes new intent
        Intent returnIntent = new Intent();
        //puts the colour into the intent to return
        returnIntent.putExtra("colour", insertText.getCurrentTextColor());
        //returns the intent and data
        setResult(Activity.RESULT_OK, returnIntent);
        //ends the activity
        finish();
    }
}
