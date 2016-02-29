/*Author: Greg Field
  App: Music School App with the fixed ui
  Description: App lets the user select an instument that
  they want to take lessons for then when the click the enrol button
  it sends a message to the screen saying " You have enroled in (instument)
  lessons"
 */
package bit.fielgm2.musicschoolapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button on click
        Button enrolBtn = (Button) findViewById(R.id.enrol);
        enrolBtn.setOnClickListener(new getButton());

        //populates the spinner with required fields
        String[] months = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};

        Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        int layoutId = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, layoutId, months);
        monthSpinner.setAdapter(monthAdapter);
    }

    public class getButton implements OnClickListener {
        @Override
        public void onClick(View v) {

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
    }

}
