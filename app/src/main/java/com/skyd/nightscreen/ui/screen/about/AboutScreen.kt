package com.skyd.nightscreen.ui.screen.about

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.skyd.nightscreen.R
import com.skyd.nightscreen.bean.OtherWorksBean
import com.skyd.nightscreen.config.Const
import com.skyd.nightscreen.ui.component.Centered
import com.skyd.nightscreen.ui.component.NsTopBar
import com.skyd.nightscreen.ui.component.NsTopBarStyle
import com.skyd.nightscreen.ui.component.dialog.NsDialog
import com.skyd.nightscreen.ui.local.LocalNavController
import com.skyd.nightscreen.ui.screen.license.LICENSE_SCREEN_ROUTE
import com.skyd.nightscreen.ui.shape.ReuleauxTriangleShape
import com.skyd.nightscreen.util.CommonUtil.getAppVersionName
import com.skyd.nightscreen.util.CommonUtil.openBrowser
import com.skyd.nightscreen.util.screenIsLand

const val ABOUT_SCREEN_ROUTE = "aboutScreen"

@Composable
fun AboutScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current
    var openSponsorDialog by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            NsTopBar(
                style = NsTopBarStyle.Large,
                title = {
                    Text(text = stringResource(R.string.about))
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) {
        val otherWorksList = rememberOtherWorksList()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = it,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (context.screenIsLand) {
                item {
                    Row(modifier = Modifier.wrapContentSize()) {
                        AppIconArea(modifier = Modifier.weight(1f))
                        AboutItemList(
                            modifier = Modifier.weight(1f),
                            openSponsorDialog = openSponsorDialog,
                            onSponsorDialogVisibleChange = { visible ->
                                openSponsorDialog = visible
                            },
                        )
                    }
                }
            } else {
                item {
                    AppIconArea()
                }
                item {
                    AboutItemList(
                        openSponsorDialog = openSponsorDialog,
                        onSponsorDialogVisibleChange = { visible -> openSponsorDialog = visible },
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.about_screen_other_works),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            itemsIndexed(items = otherWorksList) { _, item ->
                OtherWorksItem(data = item)
            }
        }
    }
}

@Composable
private fun AboutItemList(
    modifier: Modifier = Modifier,
    openSponsorDialog: Boolean,
    onSponsorDialogVisibleChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        val navController = LocalNavController.current
        AboutItem(
            painter = rememberVectorPainter(image = Icons.Default.Coffee),
            text = stringResource(id = R.string.sponsor)
        ) {
            onSponsorDialogVisibleChange(true)
        }
        Spacer(modifier = Modifier.height(20.dp))
        AboutItem(
            painter = painterResource(id = R.drawable.ic_github_24),
            text = stringResource(id = R.string.about_screen_goto_github_repo)
        ) {
            openBrowser(Const.GITHUB_REPOSITORY)
        }
        Spacer(modifier = Modifier.height(20.dp))
        AboutItem(
            painter = painterResource(id = R.drawable.ic_telegram_24),
            text = stringResource(id = R.string.about_screen_join_telegram)
        ) {
            openBrowser(Const.JOIN_TELEGRAM)
        }
        Spacer(modifier = Modifier.height(20.dp))
        AboutItem(
            painter = painterResource(id = R.drawable.ic_discord_24),
            text = stringResource(id = R.string.about_screen_join_discord)
        ) {
            openBrowser(Const.JOIN_DISCORD)
        }
        Spacer(modifier = Modifier.height(20.dp))
        AboutItem(
            painter = rememberVectorPainter(image = Icons.Default.Description),
            text = stringResource(id = R.string.license)
        ) {
            navController.navigate(LICENSE_SCREEN_ROUTE)
        }
    }

    SponsorDialog(visible = openSponsorDialog, onClose = { onSponsorDialogVisibleChange(false) })
}

@Composable
private fun AboutItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick?.invoke() }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painter = painter, contentDescription = null)
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .basicMarquee(iterations = Int.MAX_VALUE),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
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
        val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
        val animatedPress: Float by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "animatedPress"
        )

        Box(
            modifier = Modifier
                .padding(top = 50.dp, bottom = 30.dp)
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
                            modifier = Modifier.semantics { contentDescription = badgeNumber }
                        )
                    }
                }
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_icon_128),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}

@Composable
private fun rememberOtherWorksList(): List<OtherWorksBean> {
    val context = LocalContext.current
    return remember {
        listOf(
            OtherWorksBean(
                name = context.getString(R.string.about_screen_other_works_rays_name),
                icon = R.drawable.ic_rays,
                description = context.getString(R.string.about_screen_other_works_rays_description),
                url = context.getString(R.string.about_screen_other_works_rays_url)
            ),
            OtherWorksBean(
                name = context.getString(R.string.about_screen_other_works_raca_name),
                icon = R.drawable.ic_raca,
                description = context.getString(R.string.about_screen_other_works_raca_description),
                url = context.getString(R.string.about_screen_other_works_raca_url)
            ),
        )
    }
}

@Composable
private fun OtherWorksItem(
    modifier: Modifier = Modifier,
    data: OtherWorksBean,
) {
    Card(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .clickable { openBrowser(data.url) }
                .padding(15.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier
                        .size(30.dp)
                        .aspectRatio(1f),
                    model = data.icon,
                    contentDescription = data.name
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = data.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SponsorDialog(visible: Boolean, onClose: () -> Unit) {
    NsDialog(
        visible = visible,
        onDismissRequest = onClose,
        icon = { Icon(imageVector = Icons.Default.Coffee, contentDescription = null) },
        title = { Text(text = stringResource(id = R.string.sponsor)) },
        selectable = false,
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(text = stringResource(id = R.string.sponsor_description))
                Spacer(modifier = Modifier.height(6.dp))
                ListItem(
                    modifier = Modifier.clickable {
                        openBrowser(Const.SPONSOR_AFADIAN)
                        onClose()
                    },
                    headlineContent = { Text(text = stringResource(R.string.sponsor_afadian)) },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null)
                    }
                )
                Divider()
                ListItem(
                    modifier = Modifier.clickable {
                        openBrowser(Const.SPONSOR_BUY_ME_A_COFFEE)
                        onClose()
                    },
                    headlineContent = { Text(text = stringResource(R.string.sponsor_buy_me_a_coffee)) },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Coffee, contentDescription = null)
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onClose) {
                Text(text = stringResource(R.string.dialog_close))
            }
        },
    )
}