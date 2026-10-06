/*
 * Copyright (C) 2026 The galaxym12development Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.galaxym12.samsungparts;

import android.os.Bundle;

import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;

public class SamsungPartsActivity extends CollapsingToolbarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(com.android.settingslib.collapsingtoolbar.R.id.content_frame,
                            new SamsungPartsFragment())
                    .commit();
        }
    }
}
