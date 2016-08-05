package bit.fielgm2.activityweatherchecker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Greg on 3/06/2016.
 */
public class OptionsFragment extends DialogFragment
{
    //array for the options the user has ofr each database entry
    private String[] optionsArray = {"Show Weather", "Delete Activity", "Show Location", "Edit"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle)
    {
        //creates the dialog box with a list of the options available to the user
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Activity Options");
        builder.setItems(optionsArray, new OptionsListOnClick());

        return builder.create();
    }

    //onclick listener for the dialog box list options
    public class OptionsListOnClick implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ViewAllActivites myActivity = (ViewAllActivites) getActivity();

            //calls the options selected method in the activity and sends in what teh user clicked
            switch (which) {
                case 0:
                    myActivity.optionSelected(ViewAllActivites.Options.weather);
                    break;
                case 1:
                    myActivity.optionSelected(ViewAllActivites.Options.delete);
                    break;
                case 2:
                    myActivity.optionSelected(ViewAllActivites.Options.map);
                    break;
                case 3:
                    myActivity.optionSelected(ViewAllActivites.Options.edit);
                    break;
                default:
                    break;
            }
        }
    }
}
