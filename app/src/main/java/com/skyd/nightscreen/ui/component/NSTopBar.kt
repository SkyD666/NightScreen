package com.skyd.nightscreen.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ext.plus
import com.skyd.nightscreen.ext.screenIsLand

enum class NSTopBarStyle {
    Small, Large
}

@Composable
fun NSTopBar(
    modifier: Modifier = Modifier,
    style: NSTopBarStyle = NSTopBarStyle.Small,
    title: @Composable () -> Unit,
    contentPadding: @Composable () -> PaddingValues = {
        if (LocalContext.current.screenIsLand) {
            WindowInsets.navigationBars.asPaddingValues()
        } else {
            PaddingValues()
        } + WindowInsets.statusBars.asPaddingValues()
    },
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val colors = when (style) {
        NSTopBarStyle.Small -> TopAppBarDefaults.smallTopAppBarColors()
        NSTopBarStyle.Large -> TopAppBarDefaults.largeTopAppBarColors()
    }
    val scrollFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
    val appBarContainerColor by colors.containerColor(scrollFraction)
    val topBarModifier = Modifier.padding(contentPadding())
    Surface(modifier = modifier, color = appBarContainerColor) {
        when (style) {
            NSTopBarStyle.Small -> {
                SmallTopAppBar(
                    modifier = topBarModifier,
                    title = title,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = colors,
                    scrollBehavior = scrollBehavior
                )
            }
            NSTopBarStyle.Large -> {
                LargeTopAppBar(
                    modifier = topBarModifier,
                    title = title,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = colors,
                    scrollBehavior = scrollBehavior
                )
            }
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
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier.size(24.dp),
            painter = painter,
            tint = tint,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun TopBarIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = LocalContentColor.current,
    contentDescription: String?,
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier.size(24.dp),
            imageVector = imageVector,
            tint = tint,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun BackIcon(onClick: () -> Unit = {}) {
    TopBarIcon(
        imageVector = Icons.Rounded.ArrowBack,
        contentDescription = stringResource(id = R.string.back),
        onClick = onClick
    )
}
