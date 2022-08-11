package com.skyd.nightscreen.ui.component.dialog

import com.skyd.nightscreen.appContext
import com.skyd.nightscreen.ext.editor
import com.skyd.nightscreen.ext.sharedPreferences
import com.skyd.nightscreen.ui.component.LayerView


var showNightScreenLayer: Boolean = false
    set(value) {
        if (value) {
            layerView.visible()
            layerView.updateColor(calculateColor())
        } else {
            layerView.gone()
        }
        field = value
    }

var screenAlpha: Float = sharedPreferences().getFloat("screenAlpha", 0.5f)
    set(value) {
        field = value
        layerView.updateColor(calculateColor())
        sharedPreferences().editor { putFloat("screenAlpha", value) }
    }

var screenColor: Int = sharedPreferences().getInt("screenColor", 0x0)
    set(value) {
        field = value
        layerView.updateColor(calculateColor())
        sharedPreferences().editor { putInt("screenColor", value) }
    }

private val layerView by lazy { LayerView(appContext) }

private fun calculateColor(): Int {
    return (screenColor and 0xFFFFFF) or ((0xFF * screenAlpha).toInt() shl 24)
}