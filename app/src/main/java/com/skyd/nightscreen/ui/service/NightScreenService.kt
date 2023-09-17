package com.skyd.nightscreen.ui.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.core.content.ContextCompat
import com.skyd.nightscreen.ui.component.closeNightScreen
import com.skyd.nightscreen.ui.component.dialog.requestAllPermissionsWithAccessibilityAndShow
import com.skyd.nightscreen.ui.component.showNightScreenLayer


class NightScreenService : TileService() {
    override fun onStartListening() {
        super.onStartListening()

        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter().apply {
                addAction(ACTION_ACTIVE_TILE)
                addAction(ACTION_INACTIVE_TILE)
            },
            ContextCompat.RECEIVER_EXPORTED,
        )

        qsTile.state = if (showNightScreenLayer) {
            Tile.STATE_ACTIVE
        } else {
            Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }

    override fun onStopListening() {
        super.onStopListening()
        unregisterReceiver(receiver)
    }

    override fun onClick() {
        super.onClick()

        val tile = qsTile ?: return

        if (tile.state == Tile.STATE_INACTIVE) {
            requestAllPermissionsWithAccessibilityAndShow(this)
        } else {
            closeNightScreen()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val tile = qsTile ?: return
            when (intent?.action) {
                ACTION_ACTIVE_TILE -> {
                    tile.state = Tile.STATE_ACTIVE
                }

                ACTION_INACTIVE_TILE -> {
                    tile.state = Tile.STATE_INACTIVE
                }
            }
            tile.updateTile()
        }
    }

    companion object {
        const val ACTION_ACTIVE_TILE = "active"
        const val ACTION_INACTIVE_TILE = "inactive"
    }
}