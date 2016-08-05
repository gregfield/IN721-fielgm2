package bit.fielgm2.activityweatherchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class TitleActivity extends AppCompatActivity
{
    //shows the first page of the app
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        //sets the onclick listner
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.titleScreenLayout);
        layout.setOnClickListener(new LayoutOnClick());
    }

    //=======================================
    //Inner Class
    //=======================================
    //when the user clicks anywhere it starts the menu activity
    public class LayoutOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent newActivity = new Intent(TitleActivity.this, MenuActivity.class);
            startActivity(newActivity);
        }
    }
}
