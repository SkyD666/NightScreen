package com.skyd.nightscreen.ui.screen.home

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.NSTopBar
import com.skyd.nightscreen.ui.component.NSTopBarStyle
import com.skyd.nightscreen.ui.component.dialog.getNightScreenDialog

@Composable
fun HomeScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = rememberSplineBasedDecay(),
        state = rememberTopAppBarState()
    )
    val context = LocalContext.current
    Scaffold(
        topBar = {
            NSTopBar(
                style = NSTopBarStyle.Large,
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ) {
            item {
                HomeItem(
                    imageVector = Icons.Outlined.PlayArrow,
                    text = stringResource(id = R.string.run_night_screen),
                ) {
                    getNightScreenDialog(context).show()
                }
            }
            item {

                HomeItem(
                    imageVector = Icons.Outlined.Settings,
                    text = stringResource(id = R.string.settings),
                ) {

                }
            }
            item {
                HomeItem(
                    imageVector = Icons.Outlined.Info,
                    text = stringResource(id = R.string.about),
                ) {

                }
            }
        }
    }
}

@Composable
private fun HomeItem(imageVector: ImageVector, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp)
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