package com.example.raghadtaleb.project6_newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by raghadtaleb on 26/01/2018.
 */

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }

    public static class ArticlesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_items);
            String st = getString(R.string.settings_category_key);
            bindPreferenceSummaryToValue(findPreference(st));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.order_by_key)));

        }
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences per = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            onPreferenceChange(preference, per.getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object v) {
            String stringValue = v.toString();
            boolean check = preference instanceof ListPreference;
            if (check) {
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                if (index >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[index]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

    }
}
