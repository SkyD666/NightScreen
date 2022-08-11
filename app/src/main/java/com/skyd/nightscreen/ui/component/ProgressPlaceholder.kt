package com.skyd.nightscreen.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressPlaceholder(
    modifier: Modifier = Modifier,
    message: String? = null,
    onClick: (() -> Unit)? = null
) {
    Centered(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.run { if (onClick != null) clickable(onClick = onClick) else this },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
            if (message != null) {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = message,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}