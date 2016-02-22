package bit.fielgm2.moreonresources;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resourceResolver = getResources();
        int febFridays[] = resourceResolver.getIntArray(R.array.FebFridays);

        TextView displayView = (TextView) findViewById(R.id.fridaysView);
        displayView.setText("February Fridays are on: ");
        for(int i =0;i<febFridays.length;i++)
        {
            displayView.append(febFridays[i] + ", ");
        }
    }
}
