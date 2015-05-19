package com.fouram.nurumikeyboard.NurumiIME;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

/**
 * @class InformationActivity
 *
 * @brief The function of InformationActivity is showing
 *        Automata's image according to setting's conditions.
 *
 * @variable String stateAutomata is a setting's condition about Automata like automata[1, 2, 3]
 * @variable Booelan stateHand is a setting's condition about Hand's direction like left and right
 * @variable Boolean stateLanguage is a setting's condition about Language like Korean, English...
 *
 * @function onCreate
 * @function setInformImage
 *
 * @author Soyeong Park
 * @date 2015-05-08
 */
public class InformationActivity extends Activity {
    private String stateAutomata;
    private Boolean stateHand;
    private Boolean stateLanguage;
    private Boolean stateSpecial;

    /**
     * @function onCreate(Bundle savedInstanceState)
     *
     * @brief The functions of onCreate are setting Image according to setting's conditions
     *
     * @variable sharedPref is a Object including setting's conditions
     * @variable dr is to converter resource file into image
     * @variable imageView is a frame to set image
     * @variable stateAutomata is a setting's condition about Automata like automata[1, 2, 3]
     * @variable stateHand is a setting's condition about Hand's direction like left and right
     * @variable stateLanguage is a setting's condition about Language like Korean, English...
     *
     * @param savedInstanceState is setting previous data
     *
     * @author Soyeong Park
     * @date 2015-05-08
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Delete Dialog's title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Store setting's conditions at new variable using SharedPreferences
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        stateAutomata = sharedPref.getString("prefAutomata", "3");
        stateHand = sharedPref.getBoolean("prefHand", true);
        stateLanguage = sharedPref.getBoolean("prefLanguage", true);
        // TODO
    //    stateSpecial = ;

        // Set Layout to xml file
        setContentView(R.layout.information);

        // Set Image according to setting's conditions using BitmapDrawable
        BitmapDrawable dr = (BitmapDrawable)getResources().getDrawable(setInformImage());
        ImageView imageView = (ImageView)findViewById(R.id.imgView);
        imageView.setImageDrawable(dr);

        Log.i("SHAREDPREFERENCE", String.valueOf(sharedPref.getString("prefAutomata", "3")));
        Log.i("SHAREDPREFERENCE", String.valueOf(sharedPref.getBoolean("prefHand", true)));
        Log.i("SHAREDPREFERENCE", String.valueOf(sharedPref.getBoolean("prefLanguage", true)));
    }

    /**
     * @function setInformImage()
     *
     * @brief The function of setInformImage is returning an image's value according to setting's conditions
     *
     * @variable stateAutomata is a setting's condition about Automata like automata[1, 2, 3]
     * @variable stateHand is a setting's condition about Hand's direction like left and right
     * @variable stateLanguage is a setting's condition about Language like Korean, English...
     * @variable stateSpecial is a mode's condition about Special Character & Numbers.
     *
     * @return R.drawable.? is an image according to setting's conditions
     *
     * @author Soyeong Park
     * @date 2015-05-08
     */
    private int setInformImage() {
        // Hand: RIGHT && Language: Korean && Automata: 1
        if(stateHand && stateLanguage && stateAutomata.equals("1") && !stateSpecial)
            return R.drawable.img_auto1_rig_kor;
        // Hand: RIGHT && Language: Korean && Automata: 2
        else if(stateHand && stateLanguage && stateAutomata.equals("2") && !stateSpecial)
            return R.drawable.img_auto2_rig_kor;
        // Hand: RIGHT && Language: Korean && Automata: 3
        else if(stateHand && stateLanguage && stateAutomata.equals("3") && !stateSpecial)
            return R.drawable.img_auto3_rig_kor;

        // Hand: LEFT && Language: Korean && Automata: 1
        else if(!stateHand && stateLanguage && stateAutomata.equals("1") && !stateSpecial)
            return R.drawable.img_auto1_lef_kor;
        // Hand: LEFT && Language: Korean && Automata: 2
        else if(!stateHand && stateLanguage && stateAutomata.equals("2") && !stateSpecial)
            return R.drawable.img_auto2_lef_kor;
        // Hand: LEFT && Language: Korean && Automata: 3
        else if(!stateHand && stateLanguage && stateAutomata.equals("3") && !stateSpecial)
            return R.drawable.img_auto3_lef_kor;

        // Hand: RIGHT && Language: English && Automata: X
        else if(stateHand && !stateLanguage && !stateSpecial/* && stateAutomata.equals("1")*/)
            return R.drawable.img_auto_rig_eng;
        // Hand: LEFT && Language: English && Automata: X
        else if(!stateHand && !stateLanguage && !stateSpecial /*&& stateAutomata.equals("1")*/)
            return R.drawable.img_auto_lef_eng;

        // Hand: RIGHT && Language: Special Character && Automata: X
        else if(stateHand && stateSpecial)
            return R.drawable.img_auto_rig_spe;
        // Hand: LEFT && Language: Special Character && Automata: X
        else if(!stateHand && stateSpecial)
            return R.drawable.img_auto_lef_spe;

        else // default setting [RIGHT/KOREAN/AUTOMATA3]
            return R.drawable.img_auto3_rig_kor;
    }
}
