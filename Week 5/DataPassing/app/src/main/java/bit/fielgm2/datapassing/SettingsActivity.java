package bit.fielgm2.datapassing;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button returnUsrname = (Button) findViewById(R.id.returnBtn);
        returnUsrname.setOnClickListener(new returnUserNameOnClick());
    }

    public class returnUserNameOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            TextView username = (TextView) findViewById(R.id.enterUsrName);

            Intent returnIntent = new Intent();

            if (username.getText().length() >= 5) {
                returnIntent.putExtra("username", username.getText().toString());

                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
            else
            {
                Toast.makeText(SettingsActivity.this,"Username Must be at least 5 characters long", Toast.LENGTH_LONG).show();
            }
        }
    }
}
