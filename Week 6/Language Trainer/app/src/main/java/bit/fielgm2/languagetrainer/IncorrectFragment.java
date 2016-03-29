package bit.fielgm2.languagetrainer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Greg on 29/03/2016.
 */
public class IncorrectFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Incorrect!");
        builder.setPositiveButton("Continue", new continueButton());

        return builder.create();
    }

    public class continueButton implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            QuizQuestionsActivity quizActivity = (QuizQuestionsActivity) getActivity();
            quizActivity.confirmationClick(true);
        }
    }
}
