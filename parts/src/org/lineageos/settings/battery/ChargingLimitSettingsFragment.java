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
import androidx.preference.SwitchPreferenceCompat;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class ChargingLimitSettingsFragment extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private SwitchPreferenceCompat mChargingLimitPreference;
    private static final String CHARGINGLIMIT_ENABLE_KEY = "charginglimit_enable";
    private static final String CHARGINGLIMIT_NODE = "/sys/class/qcom-battery/batt_charge_health_en";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.charginglimit_settings);
        mChargingLimitPreference = (SwitchPreferenceCompat) findPreference(CHARGINGLIMIT_ENABLE_KEY);
        if (FileUtils.fileExists(CHARGINGLIMIT_NODE)) {
            mChargingLimitPreference.setEnabled(true);
            mChargingLimitPreference.setOnPreferenceChangeListener(this);
        } else {
            mChargingLimitPreference.setSummary(R.string.charginglimit_enable_summary_not_supported);
            mChargingLimitPreference.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (CHARGINGLIMIT_ENABLE_KEY.equals(preference.getKey())) {
            FileUtils.writeLine(CHARGINGLIMIT_NODE, (Boolean) newValue ? "1":"0");
        }
        return true;
    }
}
