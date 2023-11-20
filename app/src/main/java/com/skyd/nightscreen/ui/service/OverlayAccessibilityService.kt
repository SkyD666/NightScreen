package com.skyd.nightscreen.ui.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.skyd.nightscreen.BuildConfig
import com.skyd.nightscreen.ui.component.closeNightScreen
import com.skyd.nightscreen.ui.component.layerView
import com.skyd.nightscreen.util.screenHeight
import com.skyd.nightscreen.util.screenWidth

class OverlayAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {}
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED ||
            newConfig.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED
        ) {
            (getSystemService(WINDOW_SERVICE) as WindowManager).apply {
                updateViewLayout(layerView, layerView.layoutParams.apply {
                    height = baseContext.screenHeight
                    width = baseContext.screenWidth
                })
            }
        }
    }

    override fun onServiceConnected() {
        (getSystemService(WINDOW_SERVICE) as WindowManager).addView(
            layerView,
            layerView.layoutParams
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        closeNightScreen()
    }
}

fun isAccessibilityServiceRunning(context: Context): Boolean {
    val prefString: String? = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    )

    val raysAccessibilityServiceName = OverlayAccessibilityService::class.java.name

    val patterns = arrayOf(
        "${BuildConfig.APPLICATION_ID}/${raysAccessibilityServiceName}",
        raysAccessibilityServiceName.replaceFirst(
            context.packageName.substringBeforeLast(".debug"),
            "${BuildConfig.APPLICATION_ID}/"
        )
    )
    return prefString != null && patterns.any { prefString.contains(it) }
}