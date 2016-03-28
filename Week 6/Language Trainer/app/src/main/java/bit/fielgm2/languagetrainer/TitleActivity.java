package bit.fielgm2.languagetrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        RelativeLayout clickableLayout = (RelativeLayout) findViewById(R.id.titleScreenLayout);
        clickableLayout.setOnClickListener(new layoutClick());
    }

    public class layoutClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent startQuizIntent = new Intent(TitleActivity.this, QuizQuestionsActivity.class);
            startActivity(startQuizIntent);
        }
    }
}
