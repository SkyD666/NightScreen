package com.skyd.nightscreen.ext

import android.app.Activity
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.showToast
import com.skyd.nightscreen.ui.listener.dsl.requestPermissions
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

fun Activity.requestAllPermissions(
    onDenied: (permissions: MutableList<String>?, never: Boolean) -> Unit = { permissions, never ->
        var permission = ""
        if (permissions?.contains(Permission.SYSTEM_ALERT_WINDOW) == true) {
            permission += getString(R.string.alert_window_permission)
        }
        getString(R.string.request_permission_failed, permission).showToast()
    },
    onGranted: (permissions: MutableList<String>?, all: Boolean) -> Unit = { _, all ->
        if (all) getString(R.string.request_permissions_success).showToast()
    }
) {
    XXPermissions
        .with(this)
        .permission(
            Permission.SYSTEM_ALERT_WINDOW,
            Permission.NOTIFICATION_SERVICE,
            Permission.POST_NOTIFICATIONS
        )
        .requestPermissions {
            onGranted(onGranted)
            onDenied(onDenied)
        }
}