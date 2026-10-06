/*
 * Copyright (C) 2026 The galaxym12development Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.galaxym12.samsungparts;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.provider.Settings;
import android.view.Display;

public final class RefreshRateUtils {

    public static final String MODE_ADAPTIVE = "adaptive";
    public static final String MODE_HIGH = "high";
    public static final String MODE_STANDARD = "standard";

    private static final String KEY_PEAK_REFRESH_RATE = "peak_refresh_rate";
    private static final String KEY_MIN_REFRESH_RATE = "min_refresh_rate";

    private static final float STANDARD_REFRESH_RATE = 60f;
    private static final float EPSILON = 1f;

    private RefreshRateUtils() {}

    // The ILI9882N panel only has a 60 Hz mode, the others also run at 90 Hz.
    public static float getMaxRefreshRate(Context context) {
        DisplayManager dm = context.getSystemService(DisplayManager.class);
        Display display = dm.getDisplay(Display.DEFAULT_DISPLAY);
        float max = STANDARD_REFRESH_RATE;
        if (display != null) {
            for (Display.Mode mode : display.getSupportedModes()) {
                max = Math.max(max, mode.getRefreshRate());
            }
        }
        return max;
    }

    public static boolean isSupported(Context context) {
        return getMaxRefreshRate(context) > STANDARD_REFRESH_RATE + EPSILON;
    }

    public static String getMode(Context context) {
        ContentResolver resolver = context.getContentResolver();
        float max = getMaxRefreshRate(context);
        float peak = Settings.System.getFloat(resolver, KEY_PEAK_REFRESH_RATE, max);
        float min = Settings.System.getFloat(resolver, KEY_MIN_REFRESH_RATE, 0f);
        if (peak <= STANDARD_REFRESH_RATE + EPSILON) {
            return MODE_STANDARD;
        }
        if (min >= max - EPSILON) {
            return MODE_HIGH;
        }
        return MODE_ADAPTIVE;
    }

    public static void setMode(Context context, String mode) {
        ContentResolver resolver = context.getContentResolver();
        float max = getMaxRefreshRate(context);
        float peak;
        float min;
        switch (mode) {
            case MODE_HIGH:
                peak = max;
                min = max;
                break;
            case MODE_STANDARD:
                peak = STANDARD_REFRESH_RATE;
                min = 0f;
                break;
            case MODE_ADAPTIVE:
            default:
                peak = max;
                min = 0f;
                break;
        }
        Settings.System.putFloat(resolver, KEY_PEAK_REFRESH_RATE, peak);
        Settings.System.putFloat(resolver, KEY_MIN_REFRESH_RATE, min);
    }

    public static String nextMode(String mode) {
        switch (mode) {
            case MODE_ADAPTIVE:
                return MODE_HIGH;
            case MODE_HIGH:
                return MODE_STANDARD;
            case MODE_STANDARD:
            default:
                return MODE_ADAPTIVE;
        }
    }

    public static int getModeLabel(String mode) {
        switch (mode) {
            case MODE_HIGH:
                return R.string.refresh_rate_high;
            case MODE_STANDARD:
                return R.string.refresh_rate_standard;
            case MODE_ADAPTIVE:
            default:
                return R.string.refresh_rate_adaptive;
        }
    }
}
