package com.skyd.nightscreen.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.navDeepLink
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ext.requestAllPermissions
import com.skyd.nightscreen.ext.requestSystemAlertWindowPermission
import com.skyd.nightscreen.ui.NightScreenReceiver
import com.skyd.nightscreen.ui.component.showToast
import com.skyd.nightscreen.ui.local.LocalNavController
import com.skyd.nightscreen.ui.screen.about.ABOUT_SCREEN_ROUTE
import com.skyd.nightscreen.ui.screen.about.AboutScreen
import com.skyd.nightscreen.ui.screen.home.HOME_SCREEN_ROUTE
import com.skyd.nightscreen.ui.screen.home.HomeScreen
import com.skyd.nightscreen.ui.screen.license.LICENSE_SCREEN_ROUTE
import com.skyd.nightscreen.ui.screen.license.LicenseScreen
import com.skyd.nightscreen.ui.screen.settings.SETTINGS_SCREEN_ROUTE
import com.skyd.nightscreen.ui.screen.settings.SettingsScreen
import com.skyd.nightscreen.ui.service.isAccessibilityServiceRunning
import com.skyd.nightscreen.ui.theme.NightScreenTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseComposeActivity() {
    companion object {
        const val REQUEST_PERMISSION_ACTION = "requestPermission"
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberAnimatedNavController()
            CompositionLocalProvider(LocalNavController provides navController) {
                this.navController = navController
                NightScreenTheme {
                    AnimatedNavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        navController = navController,
                        startDestination = HOME_SCREEN_ROUTE,
                    ) {
                        composable(HOME_SCREEN_ROUTE) {
                            HomeScreen()
                        }
                        composable(
                            route = SETTINGS_SCREEN_ROUTE,
                            deepLinks = listOf(navDeepLink { action = SETTINGS_SCREEN_ROUTE })
                        ) {
                            SettingsScreen()
                        }
                        composable(ABOUT_SCREEN_ROUTE) {
                            AboutScreen()
                        }
                        composable(LICENSE_SCREEN_ROUTE) {
                            LicenseScreen()
                        }
                    }
                }
                doIntentAction(intent?.action)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        doIntentAction(intent?.action)
    }

    private fun doIntentAction(action: String?) {
        action ?: return
        if (action == REQUEST_PERMISSION_ACTION) {
            requestPermission()
        } else if (action == SETTINGS_SCREEN_ROUTE) {
            navController.navigate(SETTINGS_SCREEN_ROUTE) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (isAccessibilityServiceRunning(this)) {
            requestAllPermissions()
        } else {
            getString(R.string.no_accessibility_permission).showToast()
        }
    }

    private fun requestPermission() {
        requestSystemAlertWindowPermission(onGranted = {
            if (isAccessibilityServiceRunning(this)) {
                NightScreenReceiver.sendBroadcast(
                    context = this,
                    action = NightScreenReceiver.SHOW_DIALOG_ACTION
                )
            } else {
                startForResult.launch(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        })
    }
}
