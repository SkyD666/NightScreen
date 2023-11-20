package com.skyd.nightscreen.ui.screen.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.BaseSettingsItem
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
import com.skyd.nightscreen.ui.component.runAsScheduled
import com.skyd.nightscreen.ui.component.screenAlpha
import com.skyd.nightscreen.ui.component.screenColor
import com.skyd.nightscreen.ui.component.setLowestScreenBrightness
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        var runAsScheduledChecked by remember { mutableStateOf(runAsScheduled) }
        var showTimePicker by rememberSaveable { mutableStateOf(false) }
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
//            item {
//                CategorySettingsItem(
//                    text = stringResource(R.string.settings_screen_scheduled_task_settings)
//                )
//            }
//            item {
//                SwitchSettingsItem(
//                    icon = Icons.Default.Bedtime,
//                    text = stringResource(id = R.string.settings_screen_schedule_task_auto_run),
//                    description = null,
//                    checked = runAsScheduledChecked,
//                    onCheckedChange = {
//                        runAsScheduledChecked = it
//                        runAsScheduled = it
//                    },
//                )
//            }
//            item {
//                BaseSettingsItem(
//                    icon = rememberVectorPainter(image = Icons.Default.Alarm),
//                    text = stringResource(R.string.settings_screen_schedule_task_start_time),
//                    descriptionText = "",
//                    onClick = { showTimePicker = true },
//                )
//            }
//            item {
//                BaseSettingsItem(
//                    icon = rememberVectorPainter(image = Icons.Default.Snooze),
//                    text = stringResource(R.string.settings_screen_schedule_task_stop_time),
//                    descriptionText = "",
//                )
//            }
        }
    }
}
//
//@Composable
//private fun ScheduledTimePicker() {
//    val state = rememberTimePickerState()
//    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
//    val showingPicker by rememberSaveable { mutableStateOf(true) }
//    val configuration = LocalConfiguration.current
//
//    TimePickerDialog(
//        title = if (showingPicker.value) {
//            "Select Time "
//        } else {
//            "Enter Time"
//        },
//        onCancel = { showTimePicker = false },
//        onConfirm = {
//            val cal = Calendar.getInstance()
//            cal.set(Calendar.HOUR_OF_DAY, state.hour)
//            cal.set(Calendar.MINUTE, state.minute)
//            cal.isLenient = false
//            showTimePicker = false
//        },
//        toggle = {
//            if (configuration.screenHeightDp > 400) {
//                IconButton(onClick = { showingPicker.value = !showingPicker.value }) {
//                    val icon = if (showingPicker.value) {
//                        Icons.Outlined.Keyboard
//                    } else {
//                        Icons.Outlined.Schedule
//                    }
//                    Icon(
//                        icon,
//                        contentDescription = if (showingPicker.value) {
//                            "Switch to Text Input"
//                        } else {
//                            "Switch to Touch Input"
//                        }
//                    )
//                }
//            }
//        }
//    ) {
//        if (showingPicker.value && configuration.screenHeightDp > 400) {
//            TimePicker(state = state)
//        } else {
//            TimeInput(state = state)
//        }
//    }
//}