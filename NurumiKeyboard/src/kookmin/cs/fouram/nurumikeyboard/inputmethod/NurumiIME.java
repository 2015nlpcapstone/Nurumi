package kookmin.cs.fouram.nurumikeyboard.inputmethod;

import kookmin.cs.fouram.nurumikeyboard.automata.*;
import kookmin.cs.fouram.nurumikeyboard.R;
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


/////////////////////////////////////////////
/// @class NurumiIME
///kookmin.cs.fouram.nurumikeyboard.inputmethod \n
///   ㄴ NurumiIME.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description : \n
///  - Input method service class.\n
///  - This class makes user to replace keyboard.\n
/////////////////////////////////////////////
public class NurumiIME extends InputMethodService implements
		OnMKeyboardGestureListener {

	protected static final int FIVE_FINGERS = 5;
	// private final int TEN_FINGERS = 10;

	private final int KOR = 1;
	private final int ENG = 2;
	private final int SPC = 3;

	private View entireView;
	private ViewGroup vg;
	private MotionKeyboardView mKeyboardView;

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

	@Override
	public void onFinishInputView(boolean finishingInput) {
		super.onFinishInputView(finishingInput);
	}

	//////////////////////////////////////////////
	/// @fn onCreateInputView()
	/// @brief (Override method) Function information
	/// @remark
	/// - Description : When user put cursor in text box, custom layout will pop-up.\n
	/// The xml file of custom keyboard layout is in res\layout\mkeyboardlayout.xml\n
	/////////////////////////////////////////////
	@Override
	public View onCreateInputView() {
		Log.i("IME_LOG", "Location : NurumiIME - onCreateView()");

		restart = true;
		int layoutId = R.layout.motionkeyboardlayout;
		entireView = (View) getLayoutInflater().inflate(layoutId, null);
		vg = (ViewGroup) entireView;
		mKeyboardView = (MotionKeyboardView) vg.findViewById(R.id.MotionKeyboardView);
		mKeyboardView.setIme(this);
		numFingers = FIVE_FINGERS;
		motion = new int[numFingers];

		setViewId();
		setKeyboardFlag("1");
		setState();
		setToKorKeyboard();

		restartMng();

		return entireView;
	}

	//////////////////////////////////////////////
	/// @fn restartMng()
	/// @brief This method will restart the inputMethodService.
	/// @remark
	/// - Description :
	/// Because the android os will not finish the 'service' completely, \n
	/// there will be errors when the users change input method.\n
	/// So the first time when this method shows up, it will restart.
	/////////////////////////////////////////////
	private void restartMng() {
		Log.i("IME_LOG", "Location : NurumiIME - restartMng()");
		SharedPreferences sharedPref = getSharedPreferences("RestartMng", MODE_PRIVATE);
		restart = sharedPref.getBoolean("restart", true);

		SharedPreferences.Editor prefEdit = sharedPref.edit();
		prefEdit.putBoolean("restart", !restart);
		prefEdit.commit();
		if (restart)
			android.os.Process.killProcess(android.os.Process.myPid());
	}

	/////////////////////////////////////////////
	/// @fn onStartCommand
	/// @brief (Override method) When the IME service started from
	/// SettingActivity
	/// @remark
	/// - Description : Load settings preferences.
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
	 * @author Soyeong Park
	 * @date 2015-04-15
	 */
	private void setViewId() {
		Log.i("IME_LOG", "Location : NurumiIME - setViewID()");
		ibtnInform = (ImageButton) vg.findViewById(R.id.ibtn_inform);
		ibtnInform.setOnClickListener(mClickListener);
		ibtnSetting = (ImageButton) vg.findViewById(R.id.ibtn_setting);
		ibtnSetting.setOnClickListener(mClickListener);
	}

	/**
	 * @brief This method manages all View's function through one Listener like 'onClick'
	 * @brief Examples about View are Button, TextView, ImageView ...
	 * @param v has all View's ID information.
	 * @author Soyeong Park
	 * @date 2015-05-05
	 */
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			Log.i("IME_LOG", "Location : NurumiIME - onClick()");

			Intent intentService = new Intent(NurumiIME.this, NurumiIME.class);
			boolean stopServiceFlag = false;
			Intent intentActivity;
			
			if(v.getId() == R.id.ibtn_inform) {
				Log.d("IME_LOG", "Process : onClick(). Inform button. " + v.toString());
				intentActivity = new Intent(NurumiIME.this, InformationActivity.class);
			}
			else { // R.id.ibtn_setting
				Log.d("IME_LOG", "Process : onClick(). Settings button. " + v.toString());
				stopServiceFlag = true;
				intentActivity = new Intent(NurumiIME.this, SettingActivity.class);
			}
			intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentActivity);
			if(stopServiceFlag)
				stopService(intentService);
		}
	};

	@Override
	public boolean onShowInputRequested(int flags, boolean configChange) {
		Log.v("IME_LOG", "Location : NurumiIME - onShowInputReqested()");
		return true;
	}

	/////////////////////////////////////////////
	/// @fn onWindowHidden()
	/// @brief (Override method) Function information
	/// @remark
	/// - Description :
	/// Reset keyboard view when the input method window has been hidden from
	/// the user.
	/////////////////////////////////////////////
	@Override
	public void onWindowHidden() {
		super.onWindowHidden();
		Log.v("IME_LOG", "Location : NurumiIME - onWindowHidden()");
		mKeyboardView.initialize();
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

	//////////////////////////////////////////////
	/// @fn onDestroy()
	/// @brief (Override method) Destructor of IME
	/// @remark
	/// - Description : Free all bitmap objects.\n
	/// Initialize language to Korean(default value when start).
	/////////////////////////////////////////////
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("IME_LOG", "Location : NurumiIME - onDestroy()");
		if (mKeyboardView != null)
			mKeyboardView.onDestroyView();
		try {
			setToKorKeyboard();
		} catch (Exception ignore) {
		} // If memory free failed, ignore any kind of exception.
		restartMng();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/* Custom gesture listener */
	/////////////////////////////////////////////
	/// @fn onFinishGesture
	/// @brief (Override method) Gesture listener
	/// @remark
	/// - Description : Get gesture input from MKeyboardView
	/// @see
	/// kookmin.cs.fouram.nurumikeyboard.inputmethod.OnMKeyboardGestureListener#onFinishGesture(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void onFinishGesture(int[] motion) {
		Log.i("IME_LOG", "Location : NurumiIME - onFinishGesture()");

		for(int i = 0; i < numFingers; i++) {
			if(stateHand == true) // right handed
				this.motion[i] = motion[i]; // get gesture input
			else 				  // left handed
				this.motion[i] = motion[(numFingers - 1) - i]; // get gesture input
		}
		Log.d("IME_LOG", "Process : onFinishGesture(). HandSetting : " + stateHand + " (true = right | false = left)");
		

		if (changeKeyboardType(this.motion)) {
			setAutomata(keyboardTypeFlag, stateAutomata);
			Log.d("IME_LOG", "Process : onFinishGesture(). Language setting changed");
		}
		else if (automata.isAllocatedMotion(motion)) { // If motion is allocated motion, do automata.execute()
			Log.d("IME_LOG", "Process : onFinishGesture(). Write text");
			InputConnection ic = getCurrentInputConnection();
			ic.commitText(String.valueOf(automata.execute(this.motion, ic)), 1);
		}
		// Ignore if the motion is not allocated motion
	}

	/**
	 * @function setState()
	 *
	 * @brief The function of setState is to get and set SettingActivity's
	 *        information
	 *
	 * @variable stateAutomata is a setting's condition about Automata like
	 *           automata[1, 2, 3]
	 * @variable stateHand is a setting's condition about Hand's direction like
	 *           left and right
	 * @variable stateLanguage is a setting's condition about Language like
	 *           Korean, English...
	 *
	 * @author Hyungsoon Park
	 * @date 2015-05-15
	 */
	private void setState() {
		Log.i("IME_LOG", "Location : NurumiIME - setState()");
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		stateAutomata = sharedPref.getString("prefAutomata", "1");
		Log.d("IME_LOG", "Process : setState(). Pref. Automata : " + stateAutomata);
		stateHand = sharedPref.getBoolean("prefHand", true);
		Log.d("IME_LOG", "Process : setState(). Pref. Hand     : " + stateHand + "(Right = true | Left = false)");
		stateLanguage = sharedPref.getString("prefLanguage", "1");
		Log.d("IME_LOG", "Process : setState(). Pref. Language : " + stateLanguage + "(Kor = 1 | Eng = 2 | Spc = 3)");
		setKeyboardFlag(stateLanguage);		
		Log.v("IME_LOG", "Process : setState(). Flag - keyboardTypeFlag : "	+ keyboardTypeFlag + "(KOR = 1 | ENG = 2 | SPC = 3)");
		setAutomata(keyboardTypeFlag, stateAutomata);		
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
		switch (keyboardTypeFlag) {
			case KOR: {
				switch (automataType) {
					case "1":
						automata = new KoreanCheonJiIn();
						Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Kor_1");
						break;
					case "2":
						automata = new KoreanNaratgul();
						Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Kor_2");
						break;
					case "3":
						automata = new KoreanAdvancedNatartgul();
						Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Kor_3");
						break;
				}
			} break;
			case ENG:
				automata = new English();
				Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Eng");
				break;
			case SPC:
				automata = new SpecialCharacters();
				Log.v("IME_LOG", "Process : setAutomata(). Automata changed to Spc");
				break;
			default:
				Log.e("IME_LOG", "Process : setAutomata(). Error : keyboardTypeFlag error. (Value : " + stateLanguage + ")");
		}
		System.gc();
	}

	/////////////////////////////////////////////
	/// @fn changeKeyboardType
	/// @brief Function information : Change keyboard language.
	/// @remark
	/// - Description : If motion is language change motion, change preference
	/// and flag.
	/// @param motion Input motion event.
	/// @return If motion is language change motion, return true.
	///
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	private boolean changeKeyboardType(int[] motion) {
		Log.i("IME_LOG", "Location : NurumiIME - changeKeyboardType()");
		if (motion[IME_Automata.THUMB_FINGER] != IME_Automata.DIRECTION_DOT
		 || motion[IME_Automata.INDEX_FINGER] != IME_Automata.DIRECTION_DOT
		 || motion[IME_Automata.MIDLE_FINGER] != IME_Automata.DIRECTION_DOT
		 || motion[IME_Automata.RING__FINGER] != IME_Automata.DIRECTION_DOT
		 || motion[IME_Automata.PINKY_FINGER] != IME_Automata.DIRECTION_DOT) {
			Log.v("IME_LOG", "Process : changeKeyboardType(). No change");
			return false;
		}
	
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		SharedPreferences.Editor prefEdit = pref.edit();
		
		String prefLang = "";
		switch (keyboardTypeFlag) {
			case KOR:
				prefLang = "2";
				break;
			case ENG:
				prefLang = "3";
				break;
			case SPC:
				prefLang = "1";
		}
		prefEdit.putString("prefLanguage", prefLang);
		prefEdit.commit();
		setKeyboardFlag(prefLang);
		
		Log.v("IME_LOG", "Process : changeKeyboardType(). Flag - keyboardTypeFlag : " + keyboardTypeFlag + "(KOR = 1 | ENG = 2 | SPC = 3)");
		return true;
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
		if (keyboardTypeFlag != KOR) {
			setKeyboardFlag("1");
			setAutomata(keyboardTypeFlag, stateAutomata);
		}
		Log.v("IME_LOG", "Process : setToKorKeyboard(). Set keyboard to KOR");
	}

	//////////////////////////////////////////////
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
		switch (stateLanguage) {
			case "1":
				keyboardTypeFlag = KOR;
				break;
			case "2":
				keyboardTypeFlag = ENG;
				break;
			case "3":
				keyboardTypeFlag = SPC;
				break;
			default:
				Log.e("IME_LOG", "Process : setKeyboardFlag(). Error : stateLanguage error. (Value : " + stateLanguage + ")");
		}
		Log.v("IME_LOG", "Process : setKeyboardFlag(). Flag - keyboardTypeFlag : " + keyboardTypeFlag + "(KOR = 1 | ENG = 2 | SPC = 3)");
	}

}
