package com.skyd.nightscreen.ui.component.dialog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import com.skyd.nightscreen.R
import com.skyd.nightscreen.appContext
import com.skyd.nightscreen.ui.activity.MainActivity
import com.skyd.nightscreen.ui.component.alphaRange
import com.skyd.nightscreen.ui.component.screenAlpha
import com.skyd.nightscreen.ui.component.showNightScreenLayer
import com.skyd.nightscreen.ui.screen.settings.SETTINGS_SCREEN_ROUTE
import java.lang.Float.max
import java.lang.Float.min


fun getNightScreenDialog(c: Context? = null): AlertDialog {
    val context = c ?: ContextThemeWrapper(appContext, R.style.Theme_NightScreen)
    val view = LayoutInflater.from(context).inflate(
        R.layout.dialog_night_screen,
        null,
        false
    )

    val dialog = MaterialAlertDialogBuilder(context)
        .setView(view)
        .create().apply {
            window?.attributes?.dimAmount = 0f
        }

    val ivSettings = view.findViewById<Button>(R.id.btn_settings_night_screen_dialog)
    val ivPowerOff = view.findViewById<Button>(R.id.btn_power_off_night_screen_dialog)
    val slider = view.findViewById<Slider>(R.id.slider_night_screen_dialog)

    ivSettings.setOnClickListener {
        context.startActivity(Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            action = SETTINGS_SCREEN_ROUTE
        })
        dialog.dismiss()
    }
    ivPowerOff.setOnClickListener {
        showNightScreenLayer = false
        dialog.dismiss()
    }
    slider.valueFrom = 1f - alphaRange.endInclusive
    slider.valueTo = 1f - alphaRange.start
    slider.value = max(slider.valueFrom, min(1f - screenAlpha, slider.valueTo))
    slider.setLabelFormatter {
        String.format("%.1f", it * 100f) + "%"
    }
    slider.addOnChangeListener { _, value, _ ->
        screenAlpha = 1f - value
    }

    showNightScreenLayer = true

    return dialog
}
