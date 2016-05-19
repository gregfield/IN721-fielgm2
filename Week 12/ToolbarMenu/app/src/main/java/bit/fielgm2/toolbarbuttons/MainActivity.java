package bit.fielgm2.toolbarbuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String itemTitle = (String) item.getTitle();

        switch (itemTitle)
        {
            case "Home":
                Toast.makeText(MainActivity.this, "You Selected the Home button", Toast.LENGTH_SHORT).show();
                break;
            case "Settings":
                Toast.makeText(MainActivity.this, "You Selected the Settings button", Toast.LENGTH_SHORT).show();
                break;
            case "Volume":
                Toast.makeText(MainActivity.this, "You Selected the Volume button", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
