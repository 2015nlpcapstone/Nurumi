package com.nlpcapstone.nurumi_frame;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

/*
 * - Developed by Soyeong Park
 * 1) Implement Menu Frame and Delete Action Bar [03.26]
 * 2) Solve Portability (Layout, View ...) [03.26]
 * 3) Apply Background Transparency to Layout [unsolved]
 * 4) Menu Button Function [unsolved]
 *      (1) Inform
 *      (2) Tutorial
 *      (3) Mute
 *      (4) Setting [03.26]
 */
public class MainActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    ToggleButton tbtn_inform, tbtn_tutorial, tbtn_mute;
    Button btn_setting;

    String data_way_hand, data_num_finger, data_select_language;


    AlertDialog adg_setting;



    TextView tv; // sample textview (나중에 삭제)
    /**
     * This method finds View's IDs and maps them on variables.
     * 2015.03.26.
     * @author soyeong Park
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.txt_sample);

        tbtn_inform = (ToggleButton)findViewById(R.id.tbtn_inform);
        tbtn_tutorial = (ToggleButton)findViewById(R.id.tbtn_tutorial);
        tbtn_mute = (ToggleButton)findViewById(R.id.tbtn_mute);
        btn_setting = (Button)findViewById(R.id.btn_setting);

        tbtn_inform.setOnCheckedChangeListener(this);
        tbtn_tutorial.setOnCheckedChangeListener(this);
        tbtn_mute.setOnCheckedChangeListener(this);
        btn_setting.setOnClickListener(this);
    }

    /**
     * This method manages ToggleButton's function through one Listener like 'onCheckedChanged'
     * 2015.03.26.
     * @author soyeong Park
     *
     * @param buttonView Have ToggleButton's ID information
     * @param isChecked Have ToggleButton's current condition information
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tbtn_inform:
                if (tbtn_inform.isChecked()) {
                    tv.setText("INFORM_ON");
                } else {
                    tv.setText("INFORM_OFF");
                }
                break;
            case R.id.tbtn_tutorial:
                if (tbtn_tutorial.isChecked()) {
                    tv.setText("TUTORIAL_ON");
                } else {
                    tv.setText("TUTORIAL_OFF");
                }
                break;
            case R.id.tbtn_mute:
                if (tbtn_mute.isChecked()) {
                    tv.setText("MUTE_ON");
                } else {
                    tv.setText("MUTE_OFF");
                }
                break;
        }
    }

    /**
     * This method manages all View's function through one Listener like 'onClick'
     * Examples about View are Button, TextView, ImageView ...
     * 2015.03.26.
     * @author soyeong Park
     *
     * @param v v has all View's ID information
     */
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_setting:
                tv.setText("Setting");
                break;
        }
    }

}