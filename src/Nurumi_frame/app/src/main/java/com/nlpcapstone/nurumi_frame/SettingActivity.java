package com.nlpcapstone.nurumi_frame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

// TODO : GET____ 함수 필요없으면 지우기
// TODO : AlertDialog 띄어진 상태에서 화면 회전시 Dialog 사라지는 현상 해결
// TODO : AlertDialog 에서 취소 버튼 또는 back 버튼 입력 시 값이 바뀌어 저장되는 것 해결
/**
 * @file SettingActivity.java
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
public class SettingActivity extends AlertDialog implements View.OnTouchListener {

    private RadioButton rbtn_right_hand, rbtn_5finger, rbtn_kor_language;
    private RadioButton rbtn_left_hand, rbtn_10finger, rbtn_eng_language;
    private Button btn_cancel, btn_confirm;
    private boolean bool_hand, bool_finger, bool_language;

    private Context context;
    /**
     * @brief This method is custom dialog's constructor.
     *
     * @param context is object of function receiving a called Activity.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public SettingActivity(Context context) {
        super(context);
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_setting);
    }

    /**
     * @brief This method connect MainActivity to AlertDialog Layout and call Listener Method.
     *
     * @param savedInstanceState restores previous information and screen.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rbtn_right_hand = (RadioButton)findViewById(R.id.rbtn_right);
        rbtn_5finger = (RadioButton)findViewById(R.id.rbtn_5finger);
        rbtn_kor_language = (RadioButton)findViewById(R.id.rbtn_korean);

        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);

        rbtn_left_hand = (RadioButton)findViewById(R.id.rbtn_left);
        rbtn_10finger = (RadioButton)findViewById(R.id.rbtn_10finger);
        rbtn_eng_language = (RadioButton)findViewById(R.id.rbtn_english);
        Log.i("++Setting", "5");
    //    setInformation();

        btn_cancel.setOnTouchListener(this);
        btn_confirm.setOnTouchListener(this);
        Log.i("++Setting", "6");
    }

    /**
     * @brief This Method set Setting AlertDialog's changed information whenever Setting Button touches.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    private void setInformation() {
        SharedPreferences pref_setting = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);

        if(pref_setting.getString("DATA_FIRST_SETTING", "").equals("YES")) {
            rbtn_right_hand.setChecked(true);
            rbtn_5finger.setChecked(true);
            rbtn_kor_language.setChecked(true);
            Log.i("++Setting", "7");
        }
        else {
            setHand(pref_setting.getBoolean("DATA_HAND", true));
            setFinger(pref_setting.getBoolean("DATA_FINGER", true));
            setLanguage(pref_setting.getBoolean("DATA_LANGUAGE", true));
            Log.i("++Setting", "8");
        }
    }

   /**
     * @brief MainActivity calls this method owing to get information about hand's direction.
     *
     * @return bool_hand has information about hand's direction.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public boolean getHand() {
        return bool_hand;
    }
    /**
     * @brief MainActivity calls this method owing to set information about hand's direction.
     *
     * @param state if state is true then hand's direction is right(default).
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public void setHand(boolean state) {
        if(state == true) {
            rbtn_right_hand.setChecked(true);
        }
        else {
            rbtn_left_hand.setChecked(true);
        }
    }
    /**
     * @brief MainActivity calls this method owing to get or set information about the number of fingers.
     *
     * @return bool_finger has information about the number of fingers.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public boolean getFinger() {
        return bool_finger;
    }
    /**
     * @brief MainActivity calls this method owing to set information about the number of fingers.
     *
     * @param state if state is true then the number of fingers is 5(default).
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public void setFinger(boolean state) {
        if(state == true) {
            rbtn_5finger.setChecked(true);
        }
        else {
            rbtn_10finger.setChecked(true);
        }
    }
    /**
     * @brief MainActivity calls this method owing to get or set information about selected language.
     *
     * @return bool_language has information about selected language.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public boolean getLanguage() {
        return bool_language;
    }
    /**
     * @brief MainActivity calls this method owing to set or set information about selected language.
     *
     * @param state if state is true then the selected language is korean(default).
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    public void setLanguage(boolean state) {
        if(state == true) {
            rbtn_kor_language.setChecked(true);
        }
        else {
            rbtn_eng_language.setChecked(true);
        }
    }
    /**
     * @brief This method confirms state of radio buttons.
     *
     * @param v has information about confirm button or cancel button.
     * @param event has click motion's occurrence information.
     * @return false
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
   /*     Log.i("--Set", "9");
        if(v == btn_confirm) {
            bool_hand = rbtn_right_hand.isChecked();
            bool_finger = rbtn_5finger.isChecked();
            bool_language = rbtn_kor_language.isChecked();
        }
    //    dismiss();
        Log.i("--Set", "10");*/
        return false;
    }
}
