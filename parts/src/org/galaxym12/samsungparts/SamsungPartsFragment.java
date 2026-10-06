/*
 * Copyright (C) 2026 The galaxym12development Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.galaxym12.samsungparts;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SamsungPartsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_REFRESH_RATE = "refresh_rate";

    private ListPreference mRefreshRate;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.samsungparts_settings, rootKey);

        mRefreshRate = findPreference(KEY_REFRESH_RATE);
        if (!RefreshRateUtils.isSupported(requireContext())) {
            mRefreshRate.setEnabled(false);
            mRefreshRate.setSummaryProvider(null);
            mRefreshRate.setSummary(R.string.refresh_rate_unsupported);
            return;
        }
        mRefreshRate.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRefreshRate.isEnabled()) {
            mRefreshRate.setValue(RefreshRateUtils.getMode(requireContext()));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (KEY_REFRESH_RATE.equals(preference.getKey())) {
            RefreshRateUtils.setMode(requireContext(), (String) newValue);
            return true;
        }
        return false;
    }
}
