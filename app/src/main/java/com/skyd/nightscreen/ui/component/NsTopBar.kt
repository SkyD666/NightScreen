package com.skyd.nightscreen.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.largeTopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ext.popBackStackWithLifecycle
import com.skyd.nightscreen.ui.local.LocalNavController

enum class NsTopBarStyle {
    Small, Large
}

@Composable
fun NsTopBar(
    style: NsTopBarStyle = NsTopBarStyle.Small,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = { BackIcon() },
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val colors = when (style) {
        NsTopBarStyle.Small -> topAppBarColors()
        NsTopBarStyle.Large -> largeTopAppBarColors()
    }
    when (style) {
        NsTopBarStyle.Small -> {
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors,
                scrollBehavior = scrollBehavior
            )
        }
        NsTopBarStyle.Large -> {
            LargeTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                colors = colors,
                scrollBehavior = scrollBehavior
            )
        }
    }
}

@Composable
fun TopBarIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = LocalContentColor.current,
    contentDescription: String?,
) {
    NsIconButton(
        modifier = modifier,
        painter = painter,
        tint = tint,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun TopBarIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = LocalContentColor.current,
    contentDescription: String?,
) {
    NsIconButton(
        modifier = modifier,
        imageVector = imageVector,
        tint = tint,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun BackIcon() {
    val navController = LocalNavController.current
    BackIcon { navController.popBackStackWithLifecycle() }
}

@Composable
fun BackIcon(onClick: () -> Unit = {}) {
    TopBarIcon(
        imageVector = Icons.Rounded.ArrowBack,
        contentDescription = stringResource(id = R.string.back),
        onClick = onClick
    )
}
