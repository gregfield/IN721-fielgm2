package bit.fielgm2.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablet);

        Button imageviewButton =(Button) findViewById(R.id.imageVIewBtn);
        imageviewButton.setOnClickListener(new imageviewButtonOnClick());

        Button listviewButton =(Button) findViewById(R.id.listViewBtn);
        listviewButton.setOnClickListener(new listviewButtonOnClick());
    }

    public class imageviewButtonOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Fragment imageFragment = new ImageViewFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.image_fragment_container,imageFragment);
            ft.commit();
        }
    }

    public class listviewButtonOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Fragment listFragment = new ListViewFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.list_fragment_container,listFragment);
            ft.commit();
        }
    }
}
