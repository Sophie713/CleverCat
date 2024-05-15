package com.example.clevercat.activityMain.mainActivityFragments.GameFragmentUI

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ComposeLoadingIndicator(color: Color = MaterialTheme.colorScheme.primary, modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(80.dp),
        color = MaterialTheme.colorScheme.background,
        trackColor = color,
    )
}