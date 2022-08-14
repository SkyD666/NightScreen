package com.skyd.nightscreen.ui

import android.content.Intent
import android.service.quicksettings.TileService
import androidx.core.net.toUri
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.skyd.nightscreen.ui.activity.MainActivity
import com.skyd.nightscreen.ui.component.dialog.getNightScreenDialog
import com.skyd.nightscreen.ui.screen.home.HOME_SCREEN_ROUTE
import com.skyd.nightscreen.ui.screen.home.REQUEST_PERMISSION


class NightScreenService : TileService() {
    override fun onClick() {
        super.onClick()

        if (XXPermissions.isGranted(this, Permission.SYSTEM_ALERT_WINDOW)) {
            showDialog(getNightScreenDialog())
        } else {
            application.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "$HOME_SCREEN_ROUTE?$REQUEST_PERMISSION=${Permission.SYSTEM_ALERT_WINDOW}".toUri(),
                    applicationContext,
                    MainActivity::class.java
                ).apply {
                    action = "$HOME_SCREEN_ROUTE?$REQUEST_PERMISSION=${Permission.SYSTEM_ALERT_WINDOW}"
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }
}