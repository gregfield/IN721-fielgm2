package bit.fielgm2.datapassing;

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

        Button goToSettings = (Button) findViewById(R.id.settingsBtn);
        goToSettings.setOnClickListener(new goToSettingsOnClick());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if((requestCode==0)&&(resultCode== Activity.RESULT_OK))
        {
            TextView displayUserName = (TextView) findViewById(R.id.usrNameTextView);
            displayUserName.setText(data.getStringExtra("username"));
        }
    }

    public class goToSettingsOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent getUsername = new Intent(MainActivity.this, SettingsActivity.class);

            startActivityForResult(getUsername, 0);
        }
    }
}
