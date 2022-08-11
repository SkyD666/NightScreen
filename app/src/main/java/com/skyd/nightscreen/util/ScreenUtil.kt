package com.skyd.nightscreen.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import com.skyd.nightscreen.appContext

val isLand = appContext.resources.configuration.orientation ==
        Configuration.ORIENTATION_LANDSCAPE

val screenHeight: Int
    get() {
        val display = appContext.display
        val windowManager = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val height = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.height()
        } else {
            val dm = DisplayMetrics()
            display?.getRealMetrics(dm)
            if (isLand) dm.widthPixels else dm.heightPixels
        }
        return if (isLand) {
            height + statusBarHeight
        } else {
            height + statusBarHeight + navigationBarHeight
        }
    }

val screenWidth: Int
    get() {
        val display = appContext.display
        val windowManager = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            val dm = DisplayMetrics()
            display?.getRealMetrics(dm)
            if (isLand) dm.widthPixels else dm.heightPixels
        }
        return if (isLand) {
            width + navigationBarHeight
        } else {
            width
        }
    }

val statusBarHeight: Int
    get() {
        val resourceId: Int = appContext.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return appContext.resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

val navigationBarHeight: Int
    get() {
        val resourceId: Int = appContext.resources
            .getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return appContext.resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }
