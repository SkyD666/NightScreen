package com.skyd.nightscreen.ui.component.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import com.skyd.nightscreen.R
import com.skyd.nightscreen.appContext
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

    val ivSettings = view.findViewById<ImageView>(R.id.iv_settings_night_screen_dialog)
    val ivPowerOff = view.findViewById<ImageView>(R.id.iv_power_off_night_screen_dialog)
    val slider = view.findViewById<Slider>(R.id.slider_night_screen_dialog)

    ivPowerOff.setOnClickListener {
        showNightScreenLayer = false
        dialog.dismiss()
    }

    slider.value = min(screenAlpha, slider.valueTo)
    slider.setLabelFormatter {
        String.format("%.1f", it * 100f) + "%"
    }
    slider.addOnChangeListener { _, value, _ ->
        screenAlpha = value
    }

    showNightScreenLayer = true

    return dialog
}
