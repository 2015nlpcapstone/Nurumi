package com.fouram.nurumikeyboard.NurumiIME;

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

import org.w3c.dom.Text;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
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
	
	private final int KOR = 0;
	private final int ENG = 1;
	private final int SPC = 2;
	
	private int numFingers;
	private View entireView;
	private ViewGroup vg;
	private MKeyboardView mKeyboardView;
	private int[] motion;

	// Soyeong
    private ImageButton ibtnInform;
    private ImageButton ibtnSetting;

	private String stateAutomata;
	private Boolean stateHand;
	private Boolean stateLanguage;
	private IME_Automata automata;
	private int keyboardTypeFlag;

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
		int layoutId = R.layout.mkeyboardlayout;
		entireView = (View)getLayoutInflater().inflate(layoutId, null);
		vg = (ViewGroup) entireView;
		mKeyboardView = (MKeyboardView) vg.findViewById(R.id.MKeyboardView);
		mKeyboardView.setIme(this);

        mKeyboardView.setIme(this);
		numFingers = FIVE_FINGERS;
		motion = new int[numFingers];
        Log.i("++MAIN", "SUCCESS");
        setViewId();
		setState();
		keyboardTypeFlag = KOR;
		automata = new Automata_type_Kor_3();
		return entireView;
	}

	/*TODO comment*/
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
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
			Intent intentService = new Intent(NurumiIME.this, NurumiIME.class);

			switch(v.getId()) {
                case R.id.ibtn_inform:
                    Log.i("++INFORM", v.toString());
                    Intent intentInform = new Intent(NurumiIME.this, InformationActivity.class);
					intentInform.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentInform);
					stopService(intentService);
                    break;
                case R.id.ibtn_setting:
					Log.i("++SETTING", "1SUCCESS");
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

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mKeyboardView != null)
			mKeyboardView.recycleBitmap();
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
		for(int i = 0; i<numFingers; i++)
			this.motion[i] = motion[i]; // get gesture input
		if( changeKeyboardType(motion) )
			setAutomata(keyboardTypeFlag, stateAutomata);
		else {
			InputConnection ic = getCurrentInputConnection();
			ic.commitText(String.valueOf(automata.execute(motion,ic)),1);
		}
		//ic.commitText(String.valueOf(Automata_type_3.execute(motion,ic)),1);// yoon // 150412 // automata 3 
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
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		stateAutomata = sharedPref.getString("prefAutomata", "");
		stateHand = sharedPref.getBoolean("prefHand", true);
		stateLanguage = sharedPref.getBoolean("prefLanguage", true);
		Log.d("shPref", "" + stateAutomata + " " + stateHand + " " + stateLanguage);
		setAutomata(keyboardTypeFlag, stateAutomata);
	}
	
	/*TODO comment*/
	private void setAutomata(int keyboardTypeFlag, String automataType) {
		switch(keyboardTypeFlag) {
			case KOR : {
				switch(automataType) {
				case "1":
					automata = new Automata_type_Kor_1();
					break;
				case "2":
					automata = new Automata_type_Kor_2();
					break;
				case "3":
					automata = new Automata_type_Kor_3();
					break;			
				}
			} break;
			case ENG :
				automata = new Automata_type_Eng();
				break;
			case SPC :
				automata = new Automata_type_Eng();
				//automata = new Automata_type_Spc(); // not implemented
				break;
			default :
				//no nothing
		}
	}
	/*TODO comment*/
	private boolean changeKeyboardType(int[] motion) {
		if(motion[0] == IME_Automata.DIRECTION_DOT
		&& motion[1] == IME_Automata.DIRECTION_DOT
		&& motion[2] == IME_Automata.DIRECTION_DOT
		&& motion[3] == IME_Automata.DIRECTION_DOT
		&& motion[4] == IME_Automata.DIRECTION_DOT) {
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor prefEdit = pref.edit();
			switch(keyboardTypeFlag) {
				case KOR :
					prefEdit.putBoolean("prefLanguage", false);
					prefEdit.commit();
					keyboardTypeFlag = ENG;
					break;
				case ENG :
					/*
					prefEdit.putBoolean("prefSpecial", true);
					prefEdit.commit();
					 */
					keyboardTypeFlag = SPC;
					break;
				case SPC :
					prefEdit.putBoolean("prefLanguage", true);
					prefEdit.commit();
					keyboardTypeFlag = KOR;
			}
			return true;
		}
		else
			return false;
	}
	
}
