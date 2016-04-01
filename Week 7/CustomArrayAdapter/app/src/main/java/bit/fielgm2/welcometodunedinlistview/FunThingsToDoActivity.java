package bit.fielgm2.welcometodunedinlistview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FunThingsToDoActivity extends AppCompatActivity {

    FunThingsToDo[] funthingstodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_things_to_do);

        initialiseDataArray();

        FunThingsToDoArrayAdapter adapter = new FunThingsToDoArrayAdapter(this, R.layout.custom_listview_item, funthingstodo);

        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }

    public void initialiseDataArray()
    {
        Resources resMachine = getResources();
        String[] funToDoArray = resMachine.getStringArray(R.array.funThingToDoArray);
        String[] funToDoImages = resMachine.getStringArray(R.array.funThingsToDoPics);

        funthingstodo = new FunThingsToDo[funToDoArray.length];

        for (int i = 0; i < funthingstodo.length; i++)
        {
            int resId = getResources().getIdentifier(funToDoImages[i], "drawable", getPackageName());
            Drawable image = getDrawable(resId);
            funthingstodo[i] = new FunThingsToDo(funToDoArray[i], image);
        }
    }

    public class FunThingsToDoArrayAdapter extends ArrayAdapter<FunThingsToDo>
    {

        public FunThingsToDoArrayAdapter(Context context, int resource, FunThingsToDo[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container)
        {
            LayoutInflater inflater = LayoutInflater.from(FunThingsToDoActivity.this);

            View customView = inflater.inflate(R.layout.custom_listview_item, container, false);

            ImageView image = (ImageView) findViewById(R.id.listViewImage);
            TextView text  = (TextView) findViewById(R.id.listViewText);

            FunThingsToDo currentItem = getItem(position);

            image.setImageDrawable(currentItem.getImage());
            text.setText(currentItem.getFunThingToDO());

            return customView;
        }
    }
}
