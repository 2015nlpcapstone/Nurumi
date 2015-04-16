package com.fouram.nurumikeyboard.NurumiIME;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * @file Setting.java
 *
 * @brief This class occurs dialog about setting.
 * @brief This class involves information whether one or two hands mode, left or right hand mode, korean or english language.
 *
 * @return dialog about setting.
 * @data bool_hand is information direction of hand.
 * @data bool_finger is information about the number of finger.
 * @data bool_language is information about selecting language.
 *
 * @date 2015.03.27.
 * @author Soyeong Park
 */
public class Setting extends View {
    private Context context;

    private RadioButton rbtn_right_hand, rbtn_left_hand;
    private RadioButton rbtn_5finger, rbtn_10finger;
    private RadioButton rbtn_kor_language, rbtn_eng_language;
    private Button btn_cancel, btn_confirm;
    private boolean bool_hand, bool_finger, bool_language;

    /**
     * @brief This method is custom dialog's constructor.
     *
     * @param context is object of function receiving a called View.
     *
     * @author Soyeong Park
     * @date 2015-04-15
     */
    public Setting(Context context) {
        super(context);
        this.context = context;

        Log.i("+A+SETTING", "11SUCCESS");
        initSetting();

  //      btn_confirm.setOnClickListener(mClickListener_setting);
   //     btn_cancel.setOnClickListener(mClickListener_setting);

        Log.i("+A+SETTING", "22SUCCESS");


    }
    // TODO: SHAREDPREFERENCES/ DATA STORE FUNCTION
    // TODO: CANCEL, APPLY FUNCTION
    Button.OnClickListener mClickListener_setting = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_cancel:
                    Log.i("+A+SETTING", "CANCEL");

                case R.id.btn_confirm:
                    Log.i("+A+SETTING", "CONFIRM");

                    break;
            }
        }
    };


    /**
     * @function initSetting
     * @brief This method sets view ID when 'Setting' button clicks.
     *
     * @author Soyeong Park
     * @date 2015-04-15
     */
    private void initSetting() {
        rbtn_right_hand = (RadioButton)findViewById(R.id.rbtn_right);
        rbtn_left_hand = (RadioButton)findViewById(R.id.rbtn_left);

        rbtn_5finger = (RadioButton)findViewById(R.id.rbtn_5finger);
        rbtn_10finger = (RadioButton)findViewById(R.id.rbtn_10finger);

        rbtn_kor_language = (RadioButton)findViewById(R.id.rbtn_korean);
        rbtn_eng_language = (RadioButton)findViewById(R.id.rbtn_english);

        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);
    }



}
