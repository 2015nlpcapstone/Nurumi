package com.fouram.nurumikeyboard.NurumiIME;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


/**
 * Created by soyeong on 15. 5. 14..
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        setOnPreferenceChange(findPreference("prefAutomata"));
    }

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
                 if(listPreference.getValue() == null) {
                     listPreference.setValueIndex(2);
                 }
                 int index = listPreference.findIndexOfValue(stringValue);
                 preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
             }

            return true;
        }
    };

    private void setOnPreferenceChange(Preference mPreference) {
        mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

        onPreferenceChangeListener.onPreferenceChange(mPreference,
                PreferenceManager.getDefaultSharedPreferences(mPreference.getContext()).getString(mPreference.getKey(), ""));
    }
}
