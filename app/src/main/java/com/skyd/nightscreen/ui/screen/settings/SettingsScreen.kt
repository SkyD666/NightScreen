package com.skyd.nightscreen.ui.screen.settings

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.*
import com.skyd.nightscreen.ui.component.dialog.AlphaDialog
import com.skyd.nightscreen.ui.component.dialog.ColorDialog
import com.skyd.nightscreen.ui.local.LocalNavController

const val SETTINGS_SCREEN_ROUTE = "settingsScreen"

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = rememberSplineBasedDecay(),
        state = rememberTopAppBarState()
    )
    val context = LocalContext.current
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            NSTopBar(
                style = NSTopBarStyle.Large,
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    BackIcon {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { innerPadding ->
        var color by remember { mutableStateOf(screenColor) }
        var alphaColor by remember { mutableStateOf(calculatedColor) }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = innerPadding.calculateBottomPadding()
            )
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
                    checked = keepScreenOn,
                    onCheckedChange = { keepScreenOn = it },
                )
            }
        }
    }
}