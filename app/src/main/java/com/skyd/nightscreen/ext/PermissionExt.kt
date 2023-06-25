package com.skyd.nightscreen.ext

import android.app.Activity
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.NightScreenReceiver
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
    onDenied: (permissions: MutableList<String>?, never: Boolean) -> Unit = { permissions, _ ->
        val permissionList = mutableSetOf<String>()
        if (permissions?.contains(Permission.SYSTEM_ALERT_WINDOW) == true) {
            permissionList.add(getString(R.string.alert_window_permission))
        }
        if (permissions?.contains(Permission.POST_NOTIFICATIONS) == true) {
            permissionList.add(getString(R.string.post_notification_permission))
        }
        if (permissions?.contains(Permission.NOTIFICATION_SERVICE) == true) {
            permissionList.add(getString(R.string.post_notification_permission))
        }
        getString(
            R.string.request_permission_failed,
            if (permissionList.size > 1) permissionList.joinToString()
            else permissionList.firstOrNull().orEmpty()
        ).showToast()
    },
    onGranted: (permissions: MutableList<String>?, all: Boolean) -> Unit = { permissions, all ->
        if (all) getString(R.string.request_permissions_success).showToast()
        if (permissions?.contains(Permission.POST_NOTIFICATIONS) == true &&
            permissions.contains(Permission.NOTIFICATION_SERVICE) &&
            permissions.contains(Permission.WRITE_SETTINGS)
        ) {
            NightScreenReceiver.sendBroadcast(action = NightScreenReceiver.SHOW_NOTIFICATION_ACTION)
        }
    },
) {
    XXPermissions
        .with(this)
        .permission(
            Permission.SYSTEM_ALERT_WINDOW,
            Permission.POST_NOTIFICATIONS,
            Permission.NOTIFICATION_SERVICE,
            Permission.WRITE_SETTINGS,
        )
        .requestPermissions {
            onGranted(onGranted)
            onDenied(onDenied)
        }
}