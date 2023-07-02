package com.skyd.nightscreen.ui.local

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavController = compositionLocalOf<NavController> {
    error("LocalNavController not initialized!")
}