package bit.fielgm2.languagetrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        //displays score
        TextView displayScore = (TextView) findViewById(R.id.resultTextView);
        displayScore.setText("Your score is " + score + " out of 11");

        Button playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        Button exitButton = (Button) findViewById(R.id.exitBtn);

        playAgainBtn.setOnClickListener(new PlayAgainOnClick());
        exitButton.setOnClickListener(new ExitOnClick());
    }
    //goes back to begining if button clicked
    public class PlayAgainOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent startActivity = new Intent(ResultsActivity.this, TitleActivity.class);
            startActivity(startActivity);
        }
    }
    //exits application if button clicked
    public class ExitOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
