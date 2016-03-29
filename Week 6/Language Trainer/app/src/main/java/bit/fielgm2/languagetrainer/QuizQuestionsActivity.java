package bit.fielgm2.languagetrainer;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizQuestionsActivity extends AppCompatActivity {

    QuizManager quizManager;
    CorrectFragment correct;
    Question currentQuestion;
    private int questionNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        quizManager = new QuizManager();
        questionNumber = 0;

        //displays the first question
        displayQuestion();

        Button nextQuestionBtn = (Button) findViewById(R.id.nextQuestBtn);
        nextQuestionBtn.setOnClickListener(new CheckQuestion());


    }

    public void displayQuestion()
    {
        TextView displayQuestionNumber = (TextView) findViewById(R.id.questNumText);
        TextView germanNoun = (TextView) findViewById(R.id.germanNounText);
        ImageView nounImage = (ImageView) findViewById(R.id.nounImage);

        //gets the current question
        currentQuestion = quizManager.getQuestion(questionNumber);
        int resId = getResources().getIdentifier(currentQuestion.getImage(), "drawable", getPackageName());
        //displays question information
        displayQuestionNumber.setText(questionNumber);
        germanNoun.setText(currentQuestion.getNoun());
        nounImage.setImageResource(resId);
    }

    public void confirmationClick(boolean nextQuest) {
    }

    public class CheckQuestion implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            RadioButton der = (RadioButton) findViewById(R.id.derRbtn);
            RadioButton das = (RadioButton) findViewById(R.id.dasRbtn);
            RadioButton die = (RadioButton) findViewById(R.id.dieRbtn);
            String checkedBtn = "";

            if(der.isChecked()) {
                checkedBtn = der.getText().toString();
            }
            else if(das.isChecked()){
                checkedBtn = das.getText().toString();
            }
            else if(die.isChecked()){
                checkedBtn = die.getText().toString();
            }

            if(checkedBtn == currentQuestion.getArticle())
            {
                correct = new CorrectFragment();
                FragmentManager fm = getFragmentManager();
                correct.show(fm, "correct");
            }
            else if(checkedBtn == "")
            {
                Toast.makeText(QuizQuestionsActivity.this , "Please select what you think the answer is", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //incorrect dialog box
            }
        }
    }
}
