package bit.fielgm2.datapassing_settingsfromanotheractivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fill text view
        TextView insertText = (TextView) findViewById(R.id.colourtextBlack);

        for (int i=0;i<5; i++)
        {
            insertText.append(getString(R.string.ColourOfText));
        }

        //set button on click
        Button changeColourBtn = (Button) findViewById(R.id.changeColourBtn);
        changeColourBtn.setOnClickListener(new changeColourOnClick());
    }

    //resumes the activity and gets the results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if((requestCode==0)&&(resultCode== Activity.RESULT_OK))
        {
            TextView changeColourTextView = (TextView) findViewById(R.id.colourtextBlack);
            changeColourTextView.setTextColor(data.getIntExtra("colour",0));
        }
    }

    //starts the new activity to get the information from
    public class changeColourOnClick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent getColour = new Intent(MainActivity.this, SettingsActivity.class);

            startActivityForResult(getColour, 0);
        }
    }
}
