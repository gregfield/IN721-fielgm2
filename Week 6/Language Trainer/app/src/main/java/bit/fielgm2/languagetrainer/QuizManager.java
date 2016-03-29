package bit.fielgm2.languagetrainer;

import android.content.Context;
import android.content.res.Resources;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Greg on 29/03/2016.
 */
public class QuizManager
{
    private final int NUMQUESTIONS = 11;
    private int score;
    Question[] quizQuestions;

    public QuizManager(Context context)
    {
        quizQuestions = new Question[NUMQUESTIONS];

        //getting the string arrays
        String[] nouns = context.getResources().getStringArray(R.array.nouns);
        String[] englishTranslation = context.getResources().getStringArray(R.array.englishTranslation);
        String[] article = context.getResources().getStringArray(R.array.article);
        String[] gender = context.getResources().getStringArray(R.array.gender);
        String[] image = context.getResources().getStringArray(R.array.images);

        //creates each of the questions and gives it the information it needs
        for (int i = 0; i < NUMQUESTIONS; i++)
        {
            quizQuestions[i] = new Question(nouns[i], englishTranslation[i], article[i], gender[i], image[i]);
        }
        shuffle();
    }

    public Question getQuestion(int questionNumber)
    {
        return quizQuestions[questionNumber];
    }

    public void shuffle()
    {
        Random random = new Random();
        int first,second;
        for(int i = 0; i < 100; i++)
        {
            first = random.nextInt(NUMQUESTIONS);
            do
            {
                second = random.nextInt(NUMQUESTIONS);
            }while (first==second);

            swap(first,second);
        }
    }

    public void swap(int first, int second)
    {
        Question temp;
        temp = quizQuestions[first];
        quizQuestions[first] = quizQuestions[second];
        quizQuestions[second] = temp;
    }

    public int getScore(){return score;}
    public void setScore(int score){this.score = score;}
}
