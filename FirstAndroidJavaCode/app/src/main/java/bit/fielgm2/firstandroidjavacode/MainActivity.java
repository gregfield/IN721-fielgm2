package bit.fielgm2.firstandroidjavacode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectText();
    }

    private void selectText() {
        Random random = new Random();
        int breed;
        TextView dog = (TextView) findViewById(R.id.dogBreed);
        breed = random.nextInt(4);

        switch (breed) {
            case 0:
                dog.setText("Poodle");
                break;
            case 1:
                dog.setText("Labrador");
                break;
            case 2:
                dog.setText("Shar Pei");
                break;
            case 3:
                dog.setText("Newfoundland");
                break;
        }
    }

}
