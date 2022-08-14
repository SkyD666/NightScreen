package com.skyd.nightscreen.ext

import android.app.Activity
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.showToast
import com.skyd.nightscreen.ui.listener.dsl.requestSinglePermission

fun Activity.requestSystemAlertWindowPermission(
    onDenied: (never: Boolean) -> Unit = { getString(R.string.no_permission_can_not_run).showToast() },
    onGranted: () -> Unit = {}
) {
    XXPermissions
        .with(this)
        .permission(Permission.SYSTEM_ALERT_WINDOW)
        .requestSinglePermission {
            onGranted(onGranted)
            onDenied(onDenied)
        }
}