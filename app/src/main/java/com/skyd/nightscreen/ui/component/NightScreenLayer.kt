package com.skyd.nightscreen.ui.component

import android.preference.Preference
import android.provider.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.skyd.nightscreen.appContext
import com.skyd.nightscreen.ext.editor
import com.skyd.nightscreen.ext.sharedPreferences
import com.skyd.nightscreen.ui.NightScreenReceiver


var showNightScreenLayer: Boolean = false
    set(value) {
        if (value == field) return
        if (value) {
            layerView.visible()
            if (lowestScreenBrightness) {
                openLowestScreenBrightness = true
            }
            layerView.keepScreenOn = keepScreenOn
            layerView.updateColor(calculatedColor)
            NightScreenReceiver.sendBroadcast(action = NightScreenReceiver.SHOW_NOTIFICATION_ACTION)
        } else {
            layerView.gone()
            openLowestScreenBrightness = false
            NightScreenReceiver.sendBroadcast(action = NightScreenReceiver.CLOSE_NOTIFICATION_ACTION)
        }
        field = value
    }

var screenAlpha: Float = sharedPreferences().getFloat("screenAlpha", 0.5f)
    set(value) {
        field = value
        layerView.updateColor(calculatedColor)
        sharedPreferences().editor { putFloat("screenAlpha", value) }
    }

var screenColor: Int = sharedPreferences().getInt("screenColor", Color.Black.toArgb())
    set(value) {
        field = value
        layerView.updateColor(calculatedColor)
        sharedPreferences().editor { putInt("screenColor", value) }
    }

val calculatedColor: Int
    get() = (screenColor and 0xFFFFFF) or ((0xFF * screenAlpha).toInt() shl 24)

val alphaRange = 0f..0.9f

var keepScreenOn = sharedPreferences().getBoolean("keepScreenOn", false)
    set(value) {
        field = value
        layerView.keepScreenOn = value
        sharedPreferences().editor { putBoolean("keepScreenOn", value) }
    }

private var originBrightness: Int = Settings.System.getInt(
    appContext.contentResolver,
    Settings.System.SCREEN_BRIGHTNESS
)

private var originBrightnessMode: Int = Settings.System.getInt(
    appContext.contentResolver,
    Settings.System.SCREEN_BRIGHTNESS_MODE
)

var lowestScreenBrightness: Boolean =
    sharedPreferences().getBoolean("lowestScreenBrightness", false)
    set(value) {
        if (value == field) return
        field = value
        sharedPreferences().editor { putBoolean("lowestScreenBrightness", value) }
    }

var openLowestScreenBrightness: Boolean = false
    get() = lowestScreenBrightness &&
            Settings.System.getInt(
                appContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            ) == 0 &&
            Settings.System.getInt(
                appContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            ) == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
    set(value) {
        if (value == field) {
            return
        }
        if (!Settings.System.canWrite(appContext)) {
            return
        }

        if (value) {
            originBrightness = Settings.System.getInt(
                appContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
            originBrightnessMode = Settings.System.getInt(
                appContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            )
            Settings.System.putInt(
                appContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                0,
            )
            Settings.System.putInt(
                appContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL,
            )
            field = true
        } else {
            if (Settings.System.getInt(
                    appContext.contentResolver, Settings.System.SCREEN_BRIGHTNESS
                ) == 0
            ) {
                Settings.System.putInt(
                    appContext.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    originBrightness,
                )
            }
            if (Settings.System.getInt(
                    appContext.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE
                ) == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            ) {
                Settings.System.putInt(
                    appContext.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    originBrightnessMode,
                )
            }
            field = false
        }
    }

val layerView by lazy { LayerView(appContext) }