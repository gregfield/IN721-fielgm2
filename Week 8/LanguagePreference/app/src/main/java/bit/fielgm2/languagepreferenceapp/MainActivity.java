package bit.fielgm2.languagepreferenceapp;

import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor prefsEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setLangBtn = (Button) findViewById(R.id.setLanguageBtn);
        setLangBtn.setOnClickListener(new SetLanguageClickHandler());

        preferences = getSharedPreferences("prefsDemo", MODE_PRIVATE);
        prefsEditor = preferences.edit();

        String language = preferences.getString("language", null);
        if(language != null)
            languageGreeting(language);
    }

    public class SetLanguageClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
            RadioButton checkedBtn = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

            String checkedLanguage = checkedBtn.getText().toString();

            prefsEditor.putString("language", checkedLanguage);
            prefsEditor.commit();

            languageGreeting(checkedLanguage);
        }
    }

    private void languageGreeting(String language)
    {
        TextView hello = (TextView) findViewById(R.id.helloWorldTxt);
        String greeting = "";

        switch(language)
        {
            case "French":
                greeting = "Bonjour Le Monde";
            break;
            case "German":
                greeting = "Hallo Welt";
            break;
            case "Spanish":
                greeting = "Hola Mundo";
            break;
        }

        hello.setText(greeting);
    }
}
