package com.fouram.nurumikeyboard.NurumiIME;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
//import android.widget.EditText;

/////////////////////////////////////////////
/// @class NurumiIME
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� NurumiIME.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26 |
/// @section Description
///  - Prototype of gesture based keyboard.
/////////////////////////////////////////////


/////////////////////////////////////////////
/// @class NurumiIME
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� NurumiIME.java
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
	
	private final int FIVE_FINGERS = 5;
	//private final int TEN_FINGERS = 10;
	private int numFingers;
	private View entireView;
	private ViewGroup vg;
	private MKeyboardView mKeyboardView;
	private int[] motion;

    private ImageButton ibtnInform;
    private ImageButton ibtnSetting;


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

		return entireView;
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
            switch(v.getId()) {
                case R.id.ibtn_inform: // DEFAULT: OFF
                    Log.i("++INFORM", v.toString());

                    Intent intentInform = new Intent(NurumiIME.this, InformationActivity.class);
					intentInform.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentInform);

                    break;
                case R.id.ibtn_setting: // DEFAULT: OFF
                    Log.i("++SETTING", "1SUCCESS");

                    Intent intentSetting = new Intent(NurumiIME.this, SettingActivity.class);
					intentSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentSetting);

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

		String tmp;
		InputConnection ic = getCurrentInputConnection();
		/*
		if(motion[0] == 0)
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0));
		if(motion[4] == 0)
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
		else
			ic.commitText(String.valueOf('a'),1);
		*/
		ic.commitText(String.valueOf(Automata_type_3.execute(motion,ic)),1);// yoon // 150412 // automata 3 
	}
}
