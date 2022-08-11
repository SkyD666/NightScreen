package com.skyd.nightscreen.ext.theme

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * 设置状态栏和导航栏透明
 * @param root 根布局，一般传入mBinding.root，或者是window.decorView.findViewById<ViewGroup>(android.R.id.content)
 * @param darkFont 状态栏颜色是不是深色，传入null代表不更改默认颜色
 */
fun Window.transparentSystemBar(
    root: View,
    darkFont: Boolean? = context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_YES
) {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    statusBarColor = Color.TRANSPARENT
    navigationBarColor = Color.TRANSPARENT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        navigationBarDividerColor = Color.TRANSPARENT
    }

    darkFont?.let {
        // 状态栏和导航栏字体颜色
        WindowInsetsControllerCompat(this, root).let { controller ->
            controller.isAppearanceLightStatusBars = it
            controller.isAppearanceLightNavigationBars = it
        }
    }
}