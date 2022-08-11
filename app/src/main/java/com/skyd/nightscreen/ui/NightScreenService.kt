package com.skyd.nightscreen.ui

import android.service.quicksettings.TileService
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.skyd.nightscreen.ui.component.dialog.getNightScreenDialog


class NightScreenService : TileService() {
    override fun onClick() {
        super.onClick()

        if (XXPermissions.isGranted(this, Permission.SYSTEM_ALERT_WINDOW)) {
            showDialog(getNightScreenDialog())
        } else {
            XXPermissions.startPermissionActivity(this, Permission.SYSTEM_ALERT_WINDOW)
        }
    }
}