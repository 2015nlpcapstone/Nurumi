package com.fouram.nurumikeyboard.NurumiIME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * @class SettingActivity
 *
 * @brief The function of SettingActivity is storing
 *        setting's conditions about automata, hand's direction, language.
 *
 * @function onCreate
 * @listener OnPreferenceChangeListener
 * @function setOnPreferenceChange
 * @function onDestroy
 *
 * @author Soyeong Park
 * @date 2015-05-06
 */
public class SettingActivity extends PreferenceActivity {
	/**
     * @function onCreate(Bundle savedInstanceState)
     *
     * @brief The functions of onCreate are showing setting's layout
     *
     * @param savedInstanceState is setting previous data
     *
     * @author Soyeong Park
     * @date 2015-05-04
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
        setOnPreferenceChange(findPreference("prefAutomata"));
        
    }
    
    /**
     * @function OnPreferenceChange(Preference preference, Object newValue)
     *
     * @brief The function of OnPreferenceChange is detecting and
     *        storing many changes in a variety of preference
     *
     * @param prefernece is a preference object including object
     * @param newValue is an object to separate preference
     *
     * @variable stringValue is a key to separate preference
     * @variable index is an information about clicked button in ListPreference
     *
     * @author Soyeong Park
     * @date 2015-05-17
     */
    private Preference.OnPreferenceChangeListener onPreferenceChangeListener
            = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            /* ListPreference의 경우 stringValue가 entryValues이기 때문에
             * 바로 Summary를 적용하지 못하는 문제가 있음
             * 그래서 설정한 entries에서 String을 로딩하여 적용*/
             if(preference instanceof ListPreference) {
                 ListPreference listPreference = (ListPreference) preference;
                 int index = listPreference.findIndexOfValue(stringValue);
                 preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
             }

            return true;
        }
    };

    /**
     * @function setOnPrefrenceChange(Preference mPreference)
     *
     * @brief The function of setOnPreferenceChange is to detect changing setting's condition value
     * @param mPreference is having setting's conditions
     * @author Soyeong Park
     * @date 2015-05-04
     */
    private void setOnPreferenceChange(Preference mPreference) {
        mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

        onPreferenceChangeListener.onPreferenceChange(mPreference,
                PreferenceManager.getDefaultSharedPreferences(mPreference.getContext()).getString(mPreference.getKey(), ""));
    }

    /**
     * @function onDestroy
     *
     * @brief   The function of onDestroy is calling to store
     *          related information when SettingActivity's lifetime ends
     *
     * @variable stateAutomata is a setting's condition about Automata like automata[1, 2, 3]
     * @variable stateHand is a setting's condition about Hand's direction like left and right
     * @variable stateLanguage is a setting's condition about Language like Korean, English...
     * @variable intentService is a connector SettingActivity to NurumiIME
     *
     * @author Hyungsoon Park
     * @date 2015-05-15
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String stateAutomata = sharedPref.getString("prefAutomata", "3");
        Boolean stateHand = sharedPref.getBoolean("prefHand", true);
        String stateLanguage = sharedPref.getString("prefLanguage", "1");

        Intent intentService = new Intent(this, NurumiIME.class);
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentService.putExtra("prefHand", stateHand);
        intentService.putExtra("prefLanguage", stateLanguage);
        intentService.putExtra("prefAutomata", stateAutomata);

        startService(intentService);
    }
}
