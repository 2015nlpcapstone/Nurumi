package com.nlpcapstone.nurumi_frame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

/**
 * @file InformActivity.java
 *
 * @brief This Class's major function is
 * @brief introducing all finger button's input means about all character.
 *
 * @date 2015-03-28
 * @author Soyeong Park
 */
public class InformActivity extends Dialog {

    private ImageView img_inform;
    Context context;
    /**
     * @brief This method is custom dialog's constructor.
     *
     * @param context is object of function receiving a called Activity.
     *
     * @author Soyeong Park
     * @date 2015-03-28
     */
    public InformActivity(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * @brief This method connect MainActivity to CustomDialog Layout and call Listener Method.
     *
     * @param savedInstanceState restores previous information and screen.
     *
     * @author Soyeong Park
     * @date 2015-03-28
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_inform);


        img_inform = (ImageView)findViewById(R.id.img_inform);


        Log.i("inform", "66");
        setImage();

        Log.i("inform", "88");
    }

    /**
     * @brief This Method classifies according to Setting's condition
     * @brief such as DATA_HAND, DATA_FINGER, DATA_LANGUAGE.
     * @brief Also it sets an image according to Setting's condition.
     *
     * @author Soyeong Park
     * @date 2015-03-28
     */
    private void setImage() {
        SharedPreferences pref_setting = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        boolean bool_finger, bool_hand, bool_language;

        bool_finger = pref_setting.getBoolean("DATA_FINGER", true);
        bool_hand = pref_setting.getBoolean("DATA_HAND", true);
        bool_language = pref_setting.getBoolean("DATA_LANGUAGE", true);

        // TODO : 구분의 경우를 생각하여 구현해야함 (2 * 2 * 2 경우)
        if (bool_finger == true && bool_hand == true && bool_language == true) {
        // TODO : 실제로는 setImageResource 이용하여 이미지 설정해야 함
        //    img_inform.setImageResource(R.drawable.ic_launcher);
            img_inform.setBackgroundColor(Color.rgb(255, 0, 0)); // RED
        } // CASE1 : [5 손가락 / 오른손 / 한국어]
        else if(bool_finger == true && bool_hand == true && bool_language == false) {
            img_inform.setBackgroundColor(Color.rgb(0, 0, 255)); // BLUE
        } // CASE2 : [5 손가락 / 오른손 / 영어]
        else if(bool_finger == true && bool_hand == false && bool_language == true) {
            img_inform.setBackgroundColor(Color.rgb(0, 255, 0)); // GREEN
        } // CASE3 : [5 손가락 / 왼손 / 한국어]
        else if(bool_finger == true && bool_hand == false && bool_language == false) {
            img_inform.setBackgroundColor(Color.rgb(255, 255, 0)); // YELLOW
        } // CASE4 : [5 손가락 / 왼손 / 영어]
        else {
            img_inform.setBackgroundColor(Color.rgb(255, 0, 255)); // PINK
        } // [10 손가락 / 그 외]
    }
}
