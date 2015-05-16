package com.fouram.nurumikeyboard.NurumiIME;

import android.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by soyeong on 15. 5. 16..
 */
public class InformationActivity extends Activity {
    public String stateAutomata;
    public Boolean stateHand;
    public Boolean stateLanguage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        stateAutomata = sharedPref.getString("prefAutomata", "");
        stateHand = sharedPref.getBoolean("prefHand", true);
        stateLanguage = sharedPref.getBoolean("prefLanguage", true);

        setContentView(R.layout.information);

        BitmapDrawable dr = (BitmapDrawable)getResources().getDrawable(setInformImage());
        ImageView imageView = (ImageView)findViewById(R.id.imgView);
        imageView.setImageDrawable(dr);

        Log.i("SHAREDPREFERENCE", String.valueOf(sharedPref.getString("prefAutomata", "")));
        Log.i("SHAREDPREFERENCE", String.valueOf(sharedPref.getBoolean("prefHand", true)));
        Log.i("SHAREDPREFERENCE", String.valueOf(sharedPref.getBoolean("prefLanguage", true)));
    }


    public int setInformImage() {
        // Hand: RIGHT && Language: Korean && Automata: 1
        if(stateHand && stateLanguage && stateAutomata.equals("automata1"))
            return R.drawable.img_automata1;
        // Hand: RIGHT && Language: Korean && Automata: 2
        else if(stateHand && stateLanguage && stateAutomata.equals("automata2"))
            return R.drawable.img_automata2;
        // Hand: RIGHT && Language: Korean && Automata: 3
        else if(stateHand && stateLanguage && stateAutomata.equals("automata3"))
            return R.drawable.img_automata3;
    /*    // Hand: LEFT && Language: Korean && Automata: 1
        else if(!stateHand && stateLanguage && stateAutomata.equals("automata1"))
            return R.drawable.img_automata1;
        // Hand: LEFT && Language: Korean && Automata: 2
        else if(!stateHand && stateLanguage && stateAutomata.equals("automata2"))
            return R.drawable.img_automata1;
        // Hand: LEFT && Language: Korean && Automata: 3
        else if(!stateHand && stateLanguage && stateAutomata.equals("automata3"))
            return R.drawable.img_automata1;
    */
        // TODO: ADD ENGLISH MODE
        else // default setting [RIGHT/KOREAN/AUTOMATA3]
            return R.drawable.img_automata3;
    }


}
