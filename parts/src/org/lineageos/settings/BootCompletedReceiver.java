/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2019 The LineageOS Project
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

package org.lineageos.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemProperties;
import android.util.Log;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.utils.FileUtils;

public class BootCompletedReceiver extends BroadcastReceiver {

    private static final boolean DEBUG = false;
    private static final String TAG = "LegionParts";
    private static final String BYPASSCHARGING_ENABLE_KEY = "bypasscharging_enable";
    private static final String BYPASSCHARGING_NODE = "/sys/class/qcom-battery/batt_charge_bypass_en";
    private static final String CHARGINGLIMIT_ENABLE_KEY = "charginglimit_enable";
    private static final String CHARGINGLIMIT_NODE = "/sys/class/qcom-battery/batt_charge_health_en";
    private static final String TURBOCHARGING_ENABLE_KEY = "turbocharging_enable";
    private static final String TURBOCHARGING_NODE = "/sys/class/qcom-battery/batt_charge_accelerate_en";

    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (DEBUG) Log.d(TAG, "Received boot completed intent");

        boolean ByPassChargingEnabled = sharedPrefs.getBoolean(BYPASSCHARGING_ENABLE_KEY, false);
        FileUtils.writeLine(BYPASSCHARGING_NODE, ByPassChargingEnabled ? "1" : "0");

        boolean ChargingLimitEnabled = sharedPrefs.getBoolean(CHARGINGLIMIT_ENABLE_KEY, false);
        FileUtils.writeLine(CHARGINGLIMIT_NODE, ChargingLimitEnabled ? "1" : "0");

        boolean TurboChargingEnabled = sharedPrefs.getBoolean(TURBOCHARGING_ENABLE_KEY, false);
        FileUtils.writeLine(TURBOCHARGING_NODE, TurboChargingEnabled ? "1" : "0");
    }
}
