package bit.fielgm2.languagetrainer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by Greg on 29/03/2016.
 */
public class CorrectFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //what is displayed on the fragment
        builder.setTitle("Correct!");
        builder.setNeutralButton("Continue", new continueButton());

        //shows the fragment
        return builder.create();
    }

    public class continueButton implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //returns to the quiz activity
            QuizQuestionsActivity quizActivity = (QuizQuestionsActivity) getActivity();
            quizActivity.nextQuestionClick(true);
        }
    }
}
