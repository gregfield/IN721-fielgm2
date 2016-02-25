package bit.fielgm2.eventhandlerstask22;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = (EditText) findViewById(R.id.editText);
        username.setOnKeyListener(new checkUsernameSize());
    }

    public class checkUsernameSize implements View.OnKeyListener
    {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                EditText user = (EditText) findViewById(v.getId());
                if (user.getText().toString().length() == 8) {
                    Toast.makeText(MainActivity.this, "Thank You " + user.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Username must be 8 characters", Toast.LENGTH_SHORT).show();
                }
            }

            return false;
        }
    }

}
