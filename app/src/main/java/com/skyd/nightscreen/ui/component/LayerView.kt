package com.skyd.nightscreen.ui.component

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.skyd.nightscreen.util.screenHeight
import com.skyd.nightscreen.util.screenWidth


class LayerView(context: Context) : View(context) {
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutParams = WindowManager.LayoutParams(
        context.screenWidth,
        context.screenHeight,
        0,
        0,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
        },
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                or WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.TOP or Gravity.START
        fitsSystemWindows = true
    }

    private var isVisible = false
    private var bgColor = Color.TRANSPARENT

    fun updateColor(color: Int) {
        ObjectAnimator.ofInt(this, "backgroundColor", bgColor, color).apply {
            setEvaluator(ArgbEvaluator())
        }.start()
        bgColor = color
    }

    fun visible() {
        if (isVisible) return
        windowManager.addView(this, layoutParams)
        isVisible = true
    }

    fun gone() {
        if (!isVisible) return
        windowManager.removeView(this)
        isVisible = false
    }

    init {
        setBackgroundColor(bgColor)
    }
}