package com.fouram.nurumikeyboard.NurumiIME;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.jar.Attributes;

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
 * @date 2015-04-30
 * @author Soyeong Park
 */
public class Setting extends AlertDialog {
    private Context context;

    private RadioButton rbtn_right_hand, rbtn_left_hand;
    private RadioButton rbtn_5finger, rbtn_10finger;
    private RadioButton rbtn_kor_language, rbtn_eng_language;
    private boolean bool_hand, bool_finger, bool_language;

    /**
     * @brief This method is custom dialog's constructor.
     *
     * @param context is object of function receiving a called View.
     *
     * @author Soyeong Park
     * @date 2015-04-30
     */
    public Setting(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.setting);

        initSetting();

    }

    // TODO: SHAREDPREFERENCES/ DATA STORE FUNCTION

    /**
     * @function initSetting
     * @brief This method sets view ID when 'Setting' button clicks.
     *
     * @author Soyeong Park
     * @date 2015-04-30
     */
    private void initSetting() {
        rbtn_right_hand = (RadioButton)findViewById(R.id.rbtn_right);
        rbtn_left_hand = (RadioButton)findViewById(R.id.rbtn_left);

        rbtn_5finger = (RadioButton)findViewById(R.id.rbtn_5finger);
        rbtn_10finger = (RadioButton)findViewById(R.id.rbtn_10finger);

        rbtn_kor_language = (RadioButton)findViewById(R.id.rbtn_korean);
        rbtn_eng_language = (RadioButton)findViewById(R.id.rbtn_english);
    }



}
