package com.skyd.nightscreen.ui.screen.about

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import androidx.compose.animation.core.*
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.skyd.nightscreen.R
import com.skyd.nightscreen.config.Const
import com.skyd.nightscreen.ui.component.BackIcon
import com.skyd.nightscreen.ui.component.Centered
import com.skyd.nightscreen.ui.component.NSTopBar
import com.skyd.nightscreen.ui.component.NSTopBarStyle
import com.skyd.nightscreen.ui.local.LocalNavController
import com.skyd.nightscreen.ui.screen.license.LICENSE_SCREEN_ROUTE
import com.skyd.nightscreen.ui.shape.ReuleauxTriangleShape
import com.skyd.nightscreen.util.CommonUtil.getAppVersionName
import com.skyd.nightscreen.util.CommonUtil.openBrowser
import com.skyd.nightscreen.util.screenIsLand

const val ABOUT_SCREEN_ROUTE = "aboutScreen"

@Composable
fun AboutScreen() {
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
                    Text(text = stringResource(R.string.about))
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = WindowInsets.navigationBars.asPaddingValues()
        ) {
            if (context.screenIsLand) {
                item {
                    Row(modifier = Modifier.wrapContentSize()) {
                        AppIconArea(modifier = Modifier.weight(1f))
                        AboutItemList(modifier = Modifier.weight(1f))
                    }
                }
            } else {
                item {
                    AppIconArea()
                }
                item {
                    AboutItemList()
                }
            }
        }
    }
}

@Composable
private fun AboutItemList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row {
            val navController = LocalNavController.current
            AboutItem(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_github_24),
                text = stringResource(id = R.string.github)
            ) {
                openBrowser(Const.GITHUB_REPOSITORY)
            }
            Spacer(modifier = Modifier.width(20.dp))
            AboutItem(
                modifier = Modifier.weight(1f),
                painter = rememberVectorPainter(image = Icons.Outlined.Description),
                text = stringResource(id = R.string.license)
            ) {
                navController.navigate(LICENSE_SCREEN_ROUTE)
            }
        }
    }
}

@Composable
private fun AboutItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick?.invoke() }
                .padding(horizontal = 15.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painter, contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun AppIconArea(modifier: Modifier = Modifier) {
    val view = LocalView.current
    var startAnimation by remember { mutableStateOf(false) }
    Centered(modifier = modifier.fillMaxWidth()) {
        val infiniteTransition = rememberInfiniteTransition()
        val animatedPress: Float by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        Box(
            modifier = Modifier
                .padding(top = 50.dp, bottom = 50.dp)
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = ReuleauxTriangleShape(
                        rotateAngle = if (startAnimation) animatedPress else 0f
                    )
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                            startAnimation = true
                            tryAwaitRelease()
                            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            startAnimation = false
                        }
                    )
                }
        ) {
            BadgedBox(
                modifier = Modifier
                    .fillMaxSize(0.46f)
                    .align(Alignment.Center),
                badge = {
                    Badge {
                        val badgeNumber = remember { getAppVersionName() }
                        Text(
                            badgeNumber,
                            modifier = Modifier.semantics {
                                contentDescription = "$badgeNumber new notifications"
                            }
                        )
                    }
                }
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_icon_128),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }
        }
    }
}