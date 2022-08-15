package com.skyd.nightscreen.ui.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.skyd.nightscreen.appContext
import com.skyd.nightscreen.ext.editor
import com.skyd.nightscreen.ext.sharedPreferences


var showNightScreenLayer: Boolean = false
    set(value) {
        if (value) {
            layerView.visible()
            layerView.lowestScreenBrightness = lowestScreenBrightness
            layerView.keepScreenOn = keepScreenOn
            layerView.updateColor(calculatedColor)
        } else {
            layerView.gone()
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

var lowestScreenBrightness = sharedPreferences().getBoolean("lowestScreenBrightness", false)
    set(value) {
        field = value
        layerView.lowestScreenBrightness = value
        sharedPreferences().editor { putBoolean("lowestScreenBrightness", value) }
    }

private val layerView by lazy { LayerView(appContext) }