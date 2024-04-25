/*
 * Copyright (C) 2018 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings.battery;

import android.content.Context;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class ByPassChargingSettingsFragment extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private SwitchPreference mByPasschargingPreference;
    private static final String BYPASSCHARGING_ENABLE_KEY = "bypasscharging_enable";
    private static final String BYPASSCHARGING_NODE = "/sys/class/qcom-battery/batt_charge_bypass_en";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.bypasscharging_settings);
        mByPasschargingPreference = (SwitchPreference) findPreference(BYPASSCHARGING_ENABLE_KEY);
        if (FileUtils.fileExists(BYPASSCHARGING_NODE)) {
            mByPasschargingPreference.setEnabled(true);
            mByPasschargingPreference.setOnPreferenceChangeListener(this);
        } else {
            mByPasschargingPreference.setSummary(R.string.bypasscharging_enable_summary_not_supported);
            mByPasschargingPreference.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (BYPASSCHARGING_ENABLE_KEY.equals(preference.getKey())) {
            FileUtils.writeLine(BYPASSCHARGING_NODE, (Boolean) newValue ? "1":"0");
        }
        return true;
    }
}
