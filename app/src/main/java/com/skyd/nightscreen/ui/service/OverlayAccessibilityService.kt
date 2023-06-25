package com.skyd.nightscreen.ui.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.skyd.nightscreen.ui.component.layerView
import com.skyd.nightscreen.ui.component.openLowestScreenBrightness

class OverlayAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {}
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onServiceConnected() {
        (getSystemService(WINDOW_SERVICE) as WindowManager).addView(
            layerView,
            layerView.layoutParams
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        openLowestScreenBrightness = false
    }
}

fun isAccessibilityServiceRunning(context: Context): Boolean {
    val prefString: String? = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    )

    return prefString != null && prefString.contains(OverlayAccessibilityService::class.java.name)
}