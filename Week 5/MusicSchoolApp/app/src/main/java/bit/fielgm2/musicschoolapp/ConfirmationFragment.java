package bit.fielgm2.musicschoolapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Greg on 18/03/2016.
 */
public class ConfirmationFragment extends DialogFragment
{
    public Dialog onCreateDialog(Bundle savedInstanceBundle)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Are You sure you want to take music lessons?");
        builder.setPositiveButton("Yes", new yesButton());
        builder.setNegativeButton("No", new noButton());
        
        return builder.create();
    }

    public class yesButton implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
    public class noButton implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
