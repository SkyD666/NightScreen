package com.skyd.nightscreen.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ext.activity
import com.skyd.nightscreen.ext.plus
import com.skyd.nightscreen.ext.requestAllPermissions
import com.skyd.nightscreen.ui.component.NsTopBar
import com.skyd.nightscreen.ui.component.NsTopBarStyle
import com.skyd.nightscreen.ui.component.dialog.checkDialogPermissionAndShow
import com.skyd.nightscreen.ui.local.LocalNavController
import com.skyd.nightscreen.ui.screen.about.ABOUT_SCREEN_ROUTE
import com.skyd.nightscreen.ui.screen.settings.SETTINGS_SCREEN_ROUTE

const val HOME_SCREEN_ROUTE = "homeScreen"

@Composable
fun HomeScreen() {
    val navController = LocalNavController.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            NsTopBar(
                style = NsTopBarStyle.Large,
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {},
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp) + innerPadding
        ) {
            item {
                HomeItem(
                    imageVector = Icons.Default.PlayArrow,
                    text = stringResource(id = R.string.run_night_screen),
                ) {
                    checkDialogPermissionAndShow(context)
                }
            }
            item {
                HomeItem(
                    imageVector = Icons.Default.DoneAll,
                    text = stringResource(id = R.string.request_permissions),
                ) {
                    context.activity.requestAllPermissions()
                }
            }
            item {
                HomeItem(
                    imageVector = Icons.Default.Settings,
                    text = stringResource(id = R.string.settings),
                ) {
                    navController.navigate(SETTINGS_SCREEN_ROUTE)
                }
            }
            item {
                HomeItem(
                    imageVector = Icons.Default.Info,
                    text = stringResource(id = R.string.about),
                ) {
                    navController.navigate(ABOUT_SCREEN_ROUTE)
                }
            }
        }
    }
}

@Composable
private fun HomeItem(imageVector: ImageVector, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp),
        shape = RoundedCornerShape(percent = 100)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = imageVector,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = text,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1
            )
        }
    }
}