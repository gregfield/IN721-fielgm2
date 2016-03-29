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
/*
    make so only 1 radio button can be clicked
 */
public class QuizQuestionsActivity extends AppCompatActivity {

    QuizManager quizManager;
    CorrectFragment correct;
    IncorrectFragment incorrect;
    Question currentQuestion;
    private int questionNumber;
    private boolean fragmentUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        quizManager = new QuizManager(this);
        questionNumber = 0;
        fragmentUsed = false;

        //displays the first question
        displayQuestion();

        //sets the button on click handler
        Button nextQuestionBtn = (Button) findViewById(R.id.nextQuestBtn);
        nextQuestionBtn.setOnClickListener(new CheckQuestion());


    }

    //displays a question
    public void displayQuestion()
    {
        TextView displayQuestionNumber = (TextView) findViewById(R.id.questNumText);
        TextView germanNoun = (TextView) findViewById(R.id.germanNounText);
        ImageView nounImage = (ImageView) findViewById(R.id.nounImage);

        //gets the current question
        currentQuestion = quizManager.getQuestion(questionNumber);
        int resId = getResources().getIdentifier(currentQuestion.getImage(), "drawable", getPackageName());
        //displays question information
        displayQuestionNumber.setText("Question: " + String.valueOf(questionNumber+1));
        germanNoun.setText(currentQuestion.getNoun());
        nounImage.setImageResource(resId);
    }

    //what happens when the dialog fragment continue button is clicked
    public void nextQuestionClick(boolean nextQuest)
    {
        if (fragmentUsed == true) {
            correct.dismiss();
        }
        else{
            incorrect.dismiss();
        }
        //goes to result activity when all questions have been done
        if(questionNumber == 10)
        {
            Intent resultActivity = new Intent(QuizQuestionsActivity.this, ResultsActivity.class);
            resultActivity.putExtra("score", quizManager.getScore());
            startActivity(resultActivity);
            finish();
            return;
        }
        //tells the next question to display
        questionNumber++;
        displayQuestion();
    }

    //what happens when button is clicked
    public class CheckQuestion implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RadioButton der = (RadioButton) findViewById(R.id.derRbtn);
            RadioButton das = (RadioButton) findViewById(R.id.dasRbtn);
            RadioButton die = (RadioButton) findViewById(R.id.dieRbtn);
            String checkedBtn = "";
            //check what button is selected
            if (der.isChecked()) {
                checkedBtn = der.getText().toString();
                der.setChecked(false);
            } else if (das.isChecked()) {
                checkedBtn = das.getText().toString();
                das.setChecked(false);
            } else if (die.isChecked()) {
                checkedBtn = die.getText().toString();
                die.setChecked(false);
            }

            //if the button selected is correct
            if (checkedBtn.equalsIgnoreCase(currentQuestion.getArticle())) {
                quizManager.setScore(quizManager.getScore() + 1);
                //correct dialog box
                fragmentUsed = true;
                correct = new CorrectFragment();
                FragmentManager fm = getFragmentManager();
                correct.show(fm, "correct");
            } else if (checkedBtn == "") {
                //if nothing selected
                Toast.makeText(QuizQuestionsActivity.this, "Please select what you think the answer is", Toast.LENGTH_SHORT).show();
            } else//if it is incorrect
            {
                //incorrect dialog box
                fragmentUsed = false;
                incorrect = IncorrectFragment.newInstance(currentQuestion.getArticle());
                FragmentManager fm = getFragmentManager();
                incorrect.show(fm, "incorrect");
            }
        }
    }
}
