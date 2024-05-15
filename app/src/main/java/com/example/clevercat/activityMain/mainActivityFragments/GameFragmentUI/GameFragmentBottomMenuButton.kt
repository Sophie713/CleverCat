package com.example.clevercat.activityMain.mainActivityFragments.GameFragmentUI

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun GameFragmentBottomMenuButton(text: String, onClick: () -> Unit, modifier: Modifier) {
    Button(onClick = onClick, modifier) {
        Text(text = text, textAlign = TextAlign.Center, modifier = Modifier)
    }
}