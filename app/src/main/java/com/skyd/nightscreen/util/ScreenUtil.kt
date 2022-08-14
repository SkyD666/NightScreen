package com.skyd.nightscreen.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

val Context.screenIsLand: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

val Context.screenHeight: Int
    get() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val height = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.height()
        } else {
            val dm = DisplayMetrics()
            display?.getRealMetrics(dm)
            if (screenIsLand) dm.widthPixels else dm.heightPixels
        }
        return if (screenIsLand) {
            height + statusBarHeight
        } else {
            height + statusBarHeight + navigationBarHeight
        }
    }

val Context.screenWidth: Int
    get() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val width = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            val dm = DisplayMetrics()
            display?.getRealMetrics(dm)
            if (screenIsLand) dm.widthPixels else dm.heightPixels
        }
        return if (screenIsLand) {
            width + navigationBarHeight
        } else {
            width
        }
    }

val Context.statusBarHeight: Int
    get() {
        val resourceId: Int = resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

val Context.navigationBarHeight: Int
    get() {
        val resourceId: Int = resources
            .getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }
