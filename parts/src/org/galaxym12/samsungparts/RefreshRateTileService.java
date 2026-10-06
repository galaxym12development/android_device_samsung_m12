/*
 * Copyright (C) 2026 The galaxym12development Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.galaxym12.samsungparts;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class RefreshRateTileService extends TileService {

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        if (!RefreshRateUtils.isSupported(this)) {
            return;
        }
        String mode = RefreshRateUtils.getMode(this);
        RefreshRateUtils.setMode(this, RefreshRateUtils.nextMode(mode));
        updateTile();
    }

    private void updateTile() {
        Tile tile = getQsTile();
        if (tile == null) {
            return;
        }
        if (RefreshRateUtils.isSupported(this)) {
            tile.setState(Tile.STATE_ACTIVE);
            tile.setSubtitle(getString(RefreshRateUtils.getModeLabel(RefreshRateUtils.getMode(this))));
        } else {
            tile.setState(Tile.STATE_UNAVAILABLE);
            tile.setSubtitle(getString(R.string.refresh_rate_standard));
        }
        tile.updateTile();
    }
}
