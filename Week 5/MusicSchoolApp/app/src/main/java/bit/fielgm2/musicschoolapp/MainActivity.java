/*Author: Greg Field
  App: Music School App with the fixed ui
  Description: App lets the user select an instument that
  they want to take lessons for then when the click the enrol button
  it sends a message to the screen saying " You have enroled in (instument)
  lessons"
 */
package bit.fielgm2.musicschoolapp;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ConfirmationFragment confirmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button on click
        Button enrolBtn = (Button) findViewById(R.id.enrol);
        enrolBtn.setOnClickListener(new getButton());

        //populates the spinner with required fields
        Resources resMachine = getResources();
        String[] months = resMachine.getStringArray(R.array.Months);
        Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        int layoutId = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, layoutId, months);
        monthSpinner.setAdapter(monthAdapter);
    }

    public void confirmationClick(boolean choice)
    {
        confirmed.dismiss();

        if(choice == true)
        {
            //creates the radio button group
            RadioGroup instrumentGroup = (RadioGroup) findViewById(R.id.rgInstruments);
            //gets the radio button that is checked by using radio group checked id
            RadioButton chosenBtn = (RadioButton) findViewById(instrumentGroup.getCheckedRadioButtonId());
            //gets the text of the checked radio button
            String instrumentChosen = chosenBtn.getText().toString();
            //gets month
            Spinner getMonth = (Spinner) findViewById(R.id.monthSpinner);
            String month = getMonth.getSelectedItem().toString();
            //writes the instrument to the text view
            TextView chosenInstrumentText = (TextView) findViewById(R.id.enrolmentConfirm);
            chosenInstrumentText.setText("You have enroled for " + instrumentChosen + " lessons in " + month);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please Choose and Instrument to play", Toast.LENGTH_SHORT).show();
        }
    }

    public class getButton implements OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                //checks to see if an instrument has been selected
                //creates the radio button group
                RadioGroup instrumentGroup = (RadioGroup) findViewById(R.id.rgInstruments);
                //gets the radio button that is checked by using radio group checked id
                RadioButton chosenBtn = (RadioButton) findViewById(instrumentGroup.getCheckedRadioButtonId());

                //creates dialogue fragment
                confirmed = new ConfirmationFragment();
                FragmentManager fm = getFragmentManager();
                confirmed.show(fm,"confirm");
            }
            catch (NullPointerException ex)
            {
                Toast.makeText(MainActivity.this, "Please select an Instrument", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
