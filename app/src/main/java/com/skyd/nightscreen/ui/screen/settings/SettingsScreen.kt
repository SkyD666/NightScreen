package com.skyd.nightscreen.ui.screen.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.CategorySettingsItem
import com.skyd.nightscreen.ui.component.ColorSettingsItem
import com.skyd.nightscreen.ui.component.NsTopBar
import com.skyd.nightscreen.ui.component.NsTopBarStyle
import com.skyd.nightscreen.ui.component.SwitchSettingsItem
import com.skyd.nightscreen.ui.component.applyScreenBrightness
import com.skyd.nightscreen.ui.component.calculatedColor
import com.skyd.nightscreen.ui.component.dialog.AlphaDialog
import com.skyd.nightscreen.ui.component.dialog.ColorDialog
import com.skyd.nightscreen.ui.component.getLowestScreenBrightness
import com.skyd.nightscreen.ui.component.keepScreenOn
import com.skyd.nightscreen.ui.component.screenAlpha
import com.skyd.nightscreen.ui.component.screenColor
import com.skyd.nightscreen.ui.component.setLowestScreenBrightness

const val SETTINGS_SCREEN_ROUTE = "settingsScreen"

@Composable
fun SettingsScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            NsTopBar(
                style = NsTopBarStyle.Large,
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        var color by remember { mutableIntStateOf(screenColor) }
        var alphaColor by remember { mutableIntStateOf(calculatedColor) }
        var lowestScreenChecked by remember { mutableStateOf(getLowestScreenBrightness()) }
        var keepScreenOnChecked by remember { mutableStateOf(keepScreenOn) }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = innerPadding
        ) {
            item {
                CategorySettingsItem(
                    text = stringResource(id = R.string.settings_screen_color_settings)
                )
            }
            item {
                var openColorDialog by remember { mutableStateOf(false) }
                ColorSettingsItem(
                    icon = Icons.Default.ColorLens,
                    text = stringResource(id = R.string.settings_screen_screen_color),
                    description = stringResource(id = R.string.settings_screen_choice_a_color),
                    initColor = Color(color),
                    onClick = { openColorDialog = true },
                )
                if (openColorDialog) {
                    ColorDialog(
                        initColor = Color(screenColor),
                        onDismissRequest = { openColorDialog = false },
                        onColorSelected = {
                            screenColor = it.toArgb()
                            color = screenColor
                            alphaColor = calculatedColor
                        }
                    )
                }
            }
            item {
                var openAlphaDialog by remember { mutableStateOf(false) }
                ColorSettingsItem(
                    icon = Icons.Default.Opacity,
                    text = stringResource(id = R.string.settings_screen_screen_alpha),
                    description = stringResource(id = R.string.settings_screen_choice_a_alpha),
                    initColor = Color(alphaColor),
                    onClick = { openAlphaDialog = true },
                )
                if (openAlphaDialog) {
                    AlphaDialog(
                        initColor = Color(calculatedColor),
                        initAlpha = screenAlpha,
                        onDismissRequest = { openAlphaDialog = false },
                        onAlphaSelected = {
                            screenAlpha = it
                            alphaColor = calculatedColor
                        }
                    )
                }
            }
            item {
                CategorySettingsItem(
                    text = stringResource(id = R.string.settings_screen_screen_settings)
                )
            }
            item {
                SwitchSettingsItem(
                    icon = Icons.Default.Brightness7,
                    text = stringResource(id = R.string.settings_screen_keep_screen_on),
                    description = stringResource(id = R.string.settings_screen_keep_screen_on_description),
                    checked = keepScreenOnChecked,
                    onCheckedChange = {
                        keepScreenOnChecked = it
                        keepScreenOn = it
                    },
                )
            }
            item {
                SwitchSettingsItem(
                    icon = Icons.Default.Brightness5,
                    text = stringResource(id = R.string.settings_screen_lowest_screen_brightness),
                    description = stringResource(id = R.string.settings_screen_lowest_screen_brightness_description),
                    checked = lowestScreenChecked,
                    onCheckedChange = {
                        lowestScreenChecked = it
                        setLowestScreenBrightness(it)
                        applyScreenBrightness(it)
                    },
                )
            }
        }
    }
}