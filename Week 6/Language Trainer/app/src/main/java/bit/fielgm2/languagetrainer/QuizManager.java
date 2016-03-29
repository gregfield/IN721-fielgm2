package bit.fielgm2.languagetrainer;

import android.content.res.Resources;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Greg on 29/03/2016.
 */
public class QuizManager
{
    private final int NUMQUESTIONS = 11;
    Question[] quizQuestions;

    public QuizManager()
    {
        quizQuestions = new Question[NUMQUESTIONS];

        //getting the string arrays
        Resources resourceMachine = Resources.getSystem();
        String[] nouns = resourceMachine.getStringArray(R.array.nouns);
        String[] englishTranslation = resourceMachine.getStringArray(R.array.englishTranslation);
        String[] article = resourceMachine.getStringArray(R.array.article);
        String[] gender = resourceMachine.getStringArray(R.array.gender);
        String[] image = resourceMachine.getStringArray(R.array.images);

        //creates each of the questions and gives it the information it needs
        for (int i = 0; i < NUMQUESTIONS; i++)
        {
            quizQuestions[i] = new Question(nouns[i], englishTranslation[i], article[i], gender[i], image[i]);
        }
        shuffle();
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
}