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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enrolBtn = (Button) findViewById(R.id.enrol);
        enrolBtn.setOnClickListener(new getButton());
    }

    public class getButton implements OnClickListener
    {
        @Override
        public void onClick(View v) {

            //creates the radio button group
            RadioGroup instrumentGroup = (RadioGroup) findViewById(R.id.rgInstruments);
            //gets the radio button that is checked by using radio group checked id
            RadioButton chosenBtn = (RadioButton) findViewById(instrumentGroup.getCheckedRadioButtonId());
            //gets the text of the checked radio button
            String instrumentChosen = chosenBtn.getText().toString();
            //writes the instrument to the text view
            TextView chosenInstrumentText = (TextView) findViewById(R.id.enrolmentConfirm);
            chosenInstrumentText.setText("You have enroled for " + instrumentChosen + " lessons");
        }
    }
}
