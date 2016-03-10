package bit.fielgm2.welcometodunedinlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fills the list view in the drawer
        fillListView();
        //makes the list clickable
        ListView aboutDunedinListView = (ListView) findViewById(R.id.left_drawer);
        aboutDunedinListView.setOnItemClickListener(new aboutDunedinClickListener());

    }

    public void fillListView()
    {
        //creating an array adapter and populating the list view
        String[] aboutDunedin = {"Services", "Fun Things To Do", "Dining", "Shopping"};
        ArrayAdapter<String> aboutDunedinAdapter = new ArrayAdapter<String>(this,
                R.layout.listview_layout, aboutDunedin);

        ListView aboutDunedinListView = (ListView) findViewById(R.id.left_drawer);
        aboutDunedinListView.setAdapter(aboutDunedinAdapter);
    }

    public class aboutDunedinClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //seeing what was clicked in the list view and then starting that activity
            String itemSelected = (String) parent.getItemAtPosition(position);
            Intent goToIntent;

            switch(itemSelected)
            {
                case "Services":
                    goToIntent = new Intent(MainActivity.this, ServicesActivity.class);
                    break;
                case "Fun Things To Do":
                    goToIntent = new Intent(MainActivity.this, FunThingsToDoActivity.class);
                    break;
                case "Dining":
                    goToIntent = new Intent(MainActivity.this, DiningActivity.class);
                    break;
                case "Shopping":
                    goToIntent = new Intent(MainActivity.this, ShoppingActivity.class);
                    break;
                default:
                    goToIntent = null;
            }

            if (goToIntent != null)
                startActivity(goToIntent);
        }
    }
}
