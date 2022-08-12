package com.skyd.nightscreen.ui.screen.license

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skyd.nightscreen.R
import com.skyd.nightscreen.bean.LicenseBean
import com.skyd.nightscreen.ext.plus
import com.skyd.nightscreen.ui.component.BackIcon
import com.skyd.nightscreen.ui.component.NSTopBar
import com.skyd.nightscreen.ui.component.NSTopBarStyle
import com.skyd.nightscreen.ui.local.LocalNavController
import com.skyd.nightscreen.util.CommonUtil.openBrowser

const val LICENSE_SCREEN_ROUTE = "licenseScreen"

@Composable
fun LicenseScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec = rememberSplineBasedDecay(),
        state = rememberTopAppBarState()
    )
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            NSTopBar(
                style = NSTopBarStyle.Large,
                title = {
                    Text(text = stringResource(R.string.license))
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    BackIcon {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        val dataList = remember { getLicenseList() }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = WindowInsets.navigationBars.asPaddingValues() +
                    PaddingValues(vertical = 10.dp)
        ) {
            items(items = dataList) { item ->
                LicenseItem(item)
            }
        }
    }
}

@Composable
private fun LicenseItem(data: LicenseBean) {
    Card(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Column(
            modifier = Modifier
                .clickable { openBrowser(data.url) }
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = data.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = data.license,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Text(text = data.url, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun getLicenseList(): List<LicenseBean> {
    return listOf(
        LicenseBean(
            name = "Android Open Source Project",
            license = "Apache-2.0 License",
            url = "https://source.android.com/"
        ),
        LicenseBean(
            name = "Accompanist",
            license = "Apache-2.0 License",
            url = "https://github.com/google/accompanist"
        ),
        LicenseBean(
            name = "Hilt",
            license = "Apache-2.0 License",
            url = "https://github.com/googlecodelabs/android-hilt"
        ),
        LicenseBean(
            name = "Coil",
            license = "Apache-2.0 License",
            url = "https://github.com/coil-kt/coil"
        ),
        LicenseBean(
            name = "XXPermissions",
            license = "Apache-2.0 License",
            url = "https://github.com/getActivity/XXPermissions"
        ),
        LicenseBean(
            name = "kotlinx.coroutines",
            license = "Apache-2.0 License",
            url = "https://github.com/Kotlin/kotlinx.coroutines"
        ),
        LicenseBean(
            name = "Compose Color Picker",
            license = "MIT license",
            url = "https://github.com/godaddy/compose-color-picker"
        ),
    )
}