package com.example.clevercat.activityMain.mainActivityFragments.ComposeUI

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.clevercat.dataClasses.NumberItem


@Composable
fun ComposeGameFragment(modifier: Modifier, listOfNumbers: ArrayList<NumberItem>) {
    LazyVerticalGrid(columns = GridCells.Fixed(9), modifier = modifier) {
        items(listOfNumbers.size) {
            Text(text = listOfNumbers[it].numberValue.toString())
        }
    }

}

