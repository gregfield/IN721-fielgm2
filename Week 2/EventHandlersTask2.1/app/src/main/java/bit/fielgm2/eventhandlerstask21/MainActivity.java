package bit.fielgm2.eventhandlerstask21;

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

        EditText lookForText = (EditText) findViewById(R.id.textField);
        lookForText.setOnKeyListener(new lookForKeyPress());
    }

    public class lookForKeyPress implements View.OnKeyListener
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if (keyCode == KeyEvent.KEYCODE_AT) {
                Toast.makeText(MainActivity.this, "Don't Type @", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }
}
