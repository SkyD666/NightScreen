package com.skyd.nightscreen.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.skyd.nightscreen.R

@Composable
fun ColorDialog(
    initColor: Color,
    onDismissRequest: () -> Unit,
    onColorSelected: (color: Color) -> Unit
) {
    var color: Color by remember { mutableStateOf(initColor) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(imageVector = Icons.Default.ColorLens, contentDescription = null)
        },
        title = {
            Text(text = stringResource(id = R.string.color_dialog_title))
        },
        confirmButton = {
            TextButton(onClick = {
                onColorSelected(color)
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
            ClassicColorPicker(
                showAlphaBar = false,
                color = HsvColor.from(initColor),
                onColorChanged = { c: HsvColor ->
                    color = c.toColor()
                }
            )
        }
    )
}