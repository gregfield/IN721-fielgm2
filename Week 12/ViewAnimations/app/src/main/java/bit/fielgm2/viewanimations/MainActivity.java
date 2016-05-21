package bit.fielgm2.viewanimations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.standupBtn);
        button.setOnClickListener(new OnClick());
    }

    public class OnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            ImageView image = (ImageView) findViewById(R.id.imageView);

            YoYo.with(Techniques.StandUp)
                    .duration(1000)
                    .playOn(image);

        }
    }
}
