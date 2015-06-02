package com.fouram.nurumikeyboard.NurumiIME;

import com.fouram.nurumikeyboard.AutoTest.AutomataTest;
import com.fouram.nurumikeyboard.IME_Automata.Automata_type_Eng;
import com.fouram.nurumikeyboard.IME_Automata.Automata_type_Kor_1;
import com.fouram.nurumikeyboard.IME_Automata.Automata_type_Kor_2;
import com.fouram.nurumikeyboard.IME_Automata.Automata_type_Kor_3;
import com.fouram.nurumikeyboard.IME_Automata.Automata_type_Spc;
import com.fouram.nurumikeyboard.IME_Automata.IME_Automata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;

//import org.w3c.dom.Text;
//import static android.support.v4.app.ActivityCompat.startActivityForResult;
//import android.widget.EditText;


/////////////////////////////////////////////
/// @class NurumiIME
///com.fouram.nurumikeyboard.NurumiIME \n
///   ㄴ NurumiIME.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description
///  - Input method service class.\n
///  - This class makes user to replace keyboard.\n
/////////////////////////////////////////////
public class NurumiIME extends InputMethodService
implements OnMKeyboardGestureListener {

	protected static final int FIVE_FINGERS = 5;
	//private final int TEN_FINGERS = 10;

	private final int KOR = 1;
	private final int ENG = 2;
	private final int SPC = 3;

	private View entireView;
	private ViewGroup vg;
	private MKeyboardView mKeyboardView;

	private int numFingers;
	private int[] motion;

	// Soyeong
	private ImageButton ibtnInform;
	private ImageButton ibtnSetting;

	// preference
	private String stateAutomata; 
	private Boolean stateHand;
	private String stateLanguage;

	private IME_Automata automata;
	private int keyboardTypeFlag; // KOR == 1, ENG == 2, SPC == 3

	private Boolean restart;
	private AutomataTest test;

	@Override
	public void onFinishInputView(boolean finishingInput) {
		super.onFinishInputView(finishingInput);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Function information
	/// @remark
	/// - Description
	///	When user put cursor in text box,\n
	///	custom layout will popup.\n
	///	The xml file of custom keyboard layout is in res\layout\mkeyboardlayout.xml\n
	/// @see android.inputmethodservice.InputMethodService#onCreateInputView()
	/////////////////////////////////////////////
	@Override
	public View onCreateInputView() {
		Log.i("IME_LOG", "Location : NurumiIME - onCreateView()");

		restart = true;
		int layoutId = R.layout.mkeyboardlayout;
		entireView = (View)getLayoutInflater().inflate(layoutId, null);
		vg = (ViewGroup) entireView;
		mKeyboardView = (MKeyboardView) vg.findViewById(R.id.MKeyboardView);
		mKeyboardView.setIme(this);		
		numFingers = FIVE_FINGERS;
		motion = new int[numFingers];

		setViewId();
		keyboardTypeFlag = KOR;
		setState();
		setToKorKeyboard();

		restartMng();

		Log.i("IME_LOG", "Process : onCreateView(). Test start");
		test = new AutomataTest(getCurrentInputConnection());

		return entireView;
	}

	private void restartMng() {
		Log.i("IME_LOG", "Location : NurumiIME - restartMng()");
		SharedPreferences sharedPref = getSharedPreferences("RestartMng", MODE_PRIVATE);
		restart = sharedPref.getBoolean("restart", true);

		SharedPreferences.Editor prefEdit = sharedPref.edit();
		if(restart) {
			prefEdit.putBoolean("restart", false);
			prefEdit.commit();

			android.os.Process.killProcess(android.os.Process.myPid());
		} else {
			prefEdit.putBoolean("restart", true);
			prefEdit.commit();
		}
	}
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) When the IME service started from SettingActivity
	/// @remark
	/// - Description Load settings preferences.
	/// @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	/////////////////////////////////////////////
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.i("IME_LOG", "Location : NurumiIME - onStartCommand()");
		setState();		
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	/**
	 * @function setViewId
	 * @brief This method sets default value when the application executes.
	 *
	 * @data vg is Layout's View Group.
	 * @see android.inputmethodservice.InputMethodService#onCreateInputView()
	 * @author Soyeong Park
	 * @date 2015-04-15
	 */
	private void setViewId() {
		Log.i("IME_LOG", "Location : NurumiIME - setViewID()");
		ibtnInform = (ImageButton)vg.findViewById(R.id.ibtn_inform);
		ibtnInform.setOnClickListener(mClickListener);
		ibtnSetting = (ImageButton)vg.findViewById(R.id.ibtn_setting);
		ibtnSetting.setOnClickListener(mClickListener);
	}

	/**
	 * @brief This method manages all View's function through one Listener like 'onClick'
	 * @brief Examples about View are Button, TextView, ImageView ...
	 *
	 * @param v has all View's ID information.
	 *
	 * @author Soyeong Park
	 * @date 2015-05-05
	 */
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			Log.i("IME_LOG", "Location : NurumiIME - onClick()");

			Intent intentService = new Intent(NurumiIME.this, NurumiIME.class);			
			switch(v.getId()) {
			case R.id.ibtn_inform:
				Log.d("IME_LOG", "Process : onClick(). Inform button. " + v.toString());
				Intent intentInform = new Intent(NurumiIME.this, InformationActivity.class);
				intentInform.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentInform);
				break;
			case R.id.ibtn_setting:
				Log.d("IME_LOG", "Process : onClick(). Settings button. " + v.toString());
				Intent intentSetting = new Intent(NurumiIME.this, SettingActivity.class);
				intentSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentSetting);
				stopService(intentService);
				break;
			}
		}
	};

	@Override
	public boolean onShowInputRequested (int flags, boolean configChange) {
		Log.v("IME_LOG", "Location : NurumiIME - onShowInputReqested()");
		return true;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Function information
	/// @remark
	/// - Description
	///	Reset keyboard view when the input method window has been hidden from the user.
	/// @see android.inputmethodservice.InputMethodService#onWindowHidden()
	/////////////////////////////////////////////
	@Override
	public void onWindowHidden() {
		super.onWindowHidden();
		Log.v("IME_LOG", "Location : NurumiIME - onWindowHidden()");
		mKeyboardView.initialize(MKeyboardView.ALL);
	}	

	/* From here for full-screen mode */
	@Override
	public void onUpdateExtractingVisibility(EditorInfo ei) {
		setExtractViewShown(true);
	}

	@Override
	public boolean onEvaluateFullscreenMode() {
		return false;
	}

	@Override
	public boolean isFullscreenMode() {
		return true;
	}

	@Override
	public void setExtractViewShown(boolean shown) {
		super.setExtractViewShown(true);
	}


	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Destructor of IME
	/// @remark
	/// - Description Free all bitmap objects.\n
	/// Initialize language to Korean(default value when start).
	/// @see android.inputmethodservice.InputMethodService#onDestroy()
	/////////////////////////////////////////////
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("IME_LOG", "Location : NurumiIME - onDestroy()");
		if(mKeyboardView != null)
			mKeyboardView.onDestroyView();
		try {
			setToKorKeyboard();
		} catch(Exception ignore) {} // If memory free failed, ignore any kind of exception.
		restartMng();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/* Custom gesture listener */
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Gesture listener
	/// @remark
	/// - Description Get gesture input from MKeyboardView
	/// @see com.fouram.nurumikeyboard.NurumiIME.OnMKeyboardGestureListener#onFinishGesture(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void onFinishGesture(int[] motion) {
		Log.i("IME_LOG", "Location : NurumiIME - onFinishGesture()");
		test.testAutomata();
		/*
		if(stateHand == true) {// right handed
			for(int i = 0; i<numFingers; i++)
				this.motion[i] = motion[i]; // get gesture input
			Log.d("IME_LOG", "Process : onFinishGesture(). HandSetting : Right handed");
		} else { // left handed
			for(int i = 0; i<numFingers; i++)
				this.motion[i] = motion[(numFingers-1)-i]; // get gesture input
			Log.d("IME_LOG", "Process : onFinishGesture(). HandSetting : Left handed");
		}

		if( changeKeyboardType(this.motion) ) {
			setAutomata(keyboardTypeFlag, stateAutomata);
			Log.d("IME_LOG", "Process : onFinishGesture(). Language setting changed");
		}
		else if(automata.isAllocatedMotion(motion)) { // If motion is allocated motion, do automata.execute()
			Log.d("IME_LOG", "Process : onFinishGesture(). Write text");
			InputConnection ic = getCurrentInputConnection();
			ic.commitText(String.valueOf(automata.execute(this.motion,ic)),1);
		}			
		// Ignore if the motion is not allocated motion
		*/
	}

	/**
	 * @function setState()
	 *
	 * @brief The function of setState is to get and set SettingActivity's information
	 *
	 * @variable stateAutomata is a setting's condition about Automata like automata[1, 2, 3]
	 * @variable stateHand is a setting's condition about Hand's direction like left and right
	 * @variable stateLanguage is a setting's condition about Language like Korean, English...
	 *
	 * @author Hyungsoon Park
	 * @date 2015-05-15
	 */
	private void setState() {
		Log.i("IME_LOG", "Location : NurumiIME - setState()");
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		stateAutomata = sharedPref.getString("prefAutomata", "1");
		stateHand = sharedPref.getBoolean("prefHand", true);
		stateLanguage = sharedPref.getString("prefLanguage", "1");
		setKeyboardFlag(stateLanguage);

		setAutomata(keyboardTypeFlag, stateAutomata);
		Log.d("IME_LOG", "Process : setState(). Pref. Automata : " + stateAutomata);
		Log.d("IME_LOG", "Process : setState(). Pref. Hand     : " + stateHand + "(Right = true | Left = false)");
		Log.d("IME_LOG", "Process : setState(). Pref. Language : " + stateLanguage + "(Kor = 1 | Eng = 2 | Spc = 3)");
		Log.v("IME_LOG", "Process : setState(). Flag - keyboardTypeFlag : " + keyboardTypeFlag + "(KOR = 1 | ENG = 2 | SPC = 3)");
	}

	/////////////////////////////////////////////
	/// @fn setAutomata
	/// @brief Function information : Set input automata
	/// @remark
	/// - Description : Set input automata with flag and preference.
	/// @param keyboardTypeFlag Flag for language
	/// @param automataType Preference value of automata type.
	///
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void setAutomata(int keyboardTypeFlag, String automataType) {
		Log.i("IME_LOG", "Location : NurumiIME - setAutomata()");
		switch(keyboardTypeFlag) {
		case KOR : {
			switch(automataType) {
			case "1":
				automata = new Automata_type_Kor_1();
				Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Kor_1");
				break;
			case "2":
				automata = new Automata_type_Kor_2();
				Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Kor_2");
				break;
			case "3":
				automata = new Automata_type_Kor_3();
				Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Kor_3");
				break;			
			}
		} break;
		case ENG :
			automata = new Automata_type_Eng();
			Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Eng");
			break;
		case SPC :
			automata = new Automata_type_Spc();
			Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Spc");
			break;
		default :
			Log.e("IME_LOG", "Process : setAutomata(). Error : keyboardTypeFlag error. (Value : " + stateLanguage + ")");
		}
		System.gc();
	}

	/////////////////////////////////////////////
	/// @fn changeKeyboardType
	/// @brief Function information : Change keyboard language.
	/// @remark
	/// - Description : If motion is language change motion, change preference and flag.
	/// @param motion Input motion event.
	/// @return If motion is language change motion, return true.
	///
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean changeKeyboardType(int[] motion) {
		Log.i("IME_LOG", "Location : NurumiIME - changeKeyboardType()");
		if(motion[IME_Automata.THUMB_FINGER] == IME_Automata.DIRECTION_DOT
				&& motion[IME_Automata.INDEX_FINGER] == IME_Automata.DIRECTION_DOT
				&& motion[IME_Automata.MIDLE_FINGER] == IME_Automata.DIRECTION_DOT
				&& motion[IME_Automata.RING__FINGER] == IME_Automata.DIRECTION_DOT
				&& motion[IME_Automata.PINKY_FINGER] == IME_Automata.DIRECTION_DOT) {
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
			SharedPreferences.Editor prefEdit = pref.edit();
			switch(keyboardTypeFlag) {
			case KOR :
				prefEdit.putString("prefLanguage", "2");
				prefEdit.commit();
				keyboardTypeFlag = ENG;
				break;
			case ENG :
				prefEdit.putString("prefLanguage", "3");
				prefEdit.commit();
				keyboardTypeFlag = SPC;
				break;
			case SPC :
				prefEdit.putString("prefLanguage", "1");
				prefEdit.commit();
				keyboardTypeFlag = KOR;
			}
			Log.v("IME_LOG", "Process : changeKeyboardType(). Flag - keyboardTypeFlag : " + keyboardTypeFlag + "(KOR = 1 | ENG = 2 | SPC = 3)");
			return true;
		}
		else {
			Log.v("IME_LOG", "Process : changeKeyboardType(). No change");
			return false;
		}
	}

	/////////////////////////////////////////////
	/// @fn setToKorKeyboard
	/// @brief Function information : Set Language to Korean
	/// @remark
	/// - Description : Set Language to Korean forcibly
	///
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void setToKorKeyboard() {
		Log.i("IME_LOG", "Location : NurumiIME - setToKorKeyboard()");
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		SharedPreferences.Editor prefEdit = pref.edit();
		prefEdit.putString("prefLanguage", "1");
		prefEdit.commit();
		if(keyboardTypeFlag != KOR) {
			keyboardTypeFlag = KOR;
			setAutomata(keyboardTypeFlag, stateAutomata);
		}
		Log.v("IME_LOG", "Process : setToKorKeyboard(). Set keyboard to KOR");
	}

	/////////////////////////////////////////////
	/// @fn setKeyboardFlag
	/// @brief Function information : Set keyboarTypeFlag with using stateLanguage.
	/// @remark
	/// - Description : Synchronize with current language state preference.
	/// @param stateLanguage Current language state preference.
	///
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private void setKeyboardFlag(String stateLanguage) {
		Log.i("IME_LOG", "Location : NurumiIME - setKeyboardFlag()");
		switch(stateLanguage) {
		case "1" :
			keyboardTypeFlag = KOR;
			break;
		case "2" :
			keyboardTypeFlag = ENG;
			break;
		case "3" :
			keyboardTypeFlag = SPC;
			break;
		default :
			Log.e("IME_LOG", "Process : setKeyboardFlag(). Error : stateLanguage error. (Value : " + stateLanguage + ")");
		}
		Log.v("IME_LOG", "Process : setKeyboardFlag(). Flag - keyboardTypeFlag : " + keyboardTypeFlag + "(KOR = 1 | ENG = 2 | SPC = 3)");
	}

}
