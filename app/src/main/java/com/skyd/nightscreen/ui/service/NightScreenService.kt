package com.skyd.nightscreen.ui.service

import android.service.quicksettings.TileService
import com.skyd.nightscreen.ui.component.dialog.checkDialogPermissionAndShow


class NightScreenService : TileService() {
    override fun onClick() {
        super.onClick()

        checkDialogPermissionAndShow(this)
    }
}