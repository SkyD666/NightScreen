package com.skyd.nightscreen.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skyd.nightscreen.R
import com.skyd.nightscreen.ui.component.alphaRange

@Composable
fun AlphaDialog(
    initColor: Color,
    initAlpha: Float = initColor.alpha,
    onDismissRequest: () -> Unit,
    onAlphaSelected: (alpha: Float) -> Unit
) {
    val (r, g, b) = initColor
    var alpha: Float by remember { mutableFloatStateOf(initAlpha) }
    var color: Color by remember { mutableStateOf(initColor) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(imageVector = Icons.Default.Opacity, contentDescription = null)
        },
        title = {
            Text(text = stringResource(id = R.string.alpha_dialog_title))
        },
        confirmButton = {
            TextButton(onClick = {
                onAlphaSelected(alpha)
                onDismissRequest()
            }) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = color, shape = RoundedCornerShape(20.dp))
                        .wrapContentHeight(),
                    text = String.format("%.1f", alpha * 100) + "%",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(4f, 4f),
                            blurRadius = 10f
                        )
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Slider(
                    modifier = Modifier.padding(top = 10.dp),
                    value = alpha,
                    valueRange = alphaRange,
                    onValueChange = {
                        alpha = it
                        color = Color(red = r, green = g, blue = b, alpha = alpha)
                    }
                )
            }
        }
    )
}