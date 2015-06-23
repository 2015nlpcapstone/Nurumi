package kookmin.cs.fouram.nurumikeyboard.inputmethod;

import kookmin.cs.fouram.nurumikeyboard.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
	private String stateLanguage;	
	private ImageView imageView;
	
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
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("IME_LOG", "Location : InformationActivity - onCreate()");
		
		// Delete Dialog's title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Store setting's conditions at new variable using SharedPreferences
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		stateAutomata = sharedPref.getString("prefAutomata", "1");
		stateHand = sharedPref.getBoolean("prefHand", true);
		stateLanguage = sharedPref.getString("prefLanguage", "1");

		// Set Layout to xml file
		setContentView(R.layout.information);

		// Set Image according to setting's conditions using BitmapDrawable
		BitmapDrawable dr;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			dr = (BitmapDrawable)getResources().getDrawable(setInformImage(), null);
		else
			dr = (BitmapDrawable)getResources().getDrawable(setInformImage());
		imageView = (ImageView)findViewById(R.id.imgView);
		imageView.setImageDrawable(dr);

		Log.d("IME_LOG", "Process : onCreate(). Pref. Automata : " + String.valueOf(sharedPref.getString("prefAutomata", "1")));
		Log.d("IME_LOG", "Process : onCreate(). Pref. Hand     : " + String.valueOf(sharedPref.getBoolean("prefHand", true)) + "(Right = true | Left = false)");
		Log.d("IME_LOG", "Process : onCreate(). Pref. Language : " + String.valueOf(sharedPref.getString("prefLanguage", "1")) + "(Kor = 1 | Eng = 2 | Spc = 3)");
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
	* @return R.drawable.* is an image according to setting's conditions
	*
	* @author Soyeong Park
	* @date 2015-05-08
	*/
	private int setInformImage() {
		Log.i("IME_LOG", "Location : InformationActivity - setInformImage()");
		if(stateHand) { // right hand
			if(stateLanguage.equals("1")) {
				if(stateAutomata.equals("1"))
					return R.drawable.img_auto1_rig_kor;
				else if(stateAutomata.equals("2"))
					return R.drawable.img_auto2_rig_kor;
				else
					return R.drawable.img_auto3_rig_kor;
			}
			else if(stateLanguage.equals("2"))
				return R.drawable.img_auto_rig_eng;
			else
				return R.drawable.img_auto_rig_spe;
		}
		else { // left hand
			if(stateLanguage.equals("1")) { // Korean
				if(stateAutomata.equals("1"))
					return R.drawable.img_auto1_lef_kor;
				else if(stateAutomata.equals("2"))
					return R.drawable.img_auto2_lef_kor;
				else
					return R.drawable.img_auto3_lef_kor;
			}
			else if(stateLanguage.equals("2"))
				return R.drawable.img_auto_lef_eng;
			else
				return R.drawable.img_auto_lef_spe;
		}
	}
	
	/////////////////////////////////////////////
	/// @fn onDestroy()
	/// @brief (Override method) Destructor for evade memory leakage.
	/// @remark
	/// - Description
	/////////////////////////////////////////////
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("IME_LOG", "Location : InformationActivity - onDestroy()");
		try {
			if(imageView != null) {
				BitmapDrawable dr = (BitmapDrawable) imageView.getDrawable();
				if (dr != null)
					dr.setCallback(null);
				imageView.setImageDrawable(null);
			}
		} catch (Exception ignore) {}
	}
	
}
