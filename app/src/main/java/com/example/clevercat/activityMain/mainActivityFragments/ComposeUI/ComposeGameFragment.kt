package com.example.clevercat.activityMain.mainActivityFragments.ComposeUI

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.clevercat.activityMain.iterfaces.GameOperationsInterface
import com.example.clevercat.activityMain.iterfaces.GameplayInterface
import com.example.clevercat.dataClasses.NumberItem

@Composable
fun ComposeGameFragment(
    modifier: Modifier,
    listOfNumbers: SnapshotStateList<NumberItem>,
    gameOperationsInterface: GameOperationsInterface,
    gameplayInterface: GameplayInterface
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (buttonsLayout, grid) = createRefs()
        LazyVerticalGrid(
            columns = GridCells.Fixed(9),
            modifier = modifier.constrainAs(grid) {
                top.linkTo(parent.top, margin = Dp(16f))
                bottom.linkTo(buttonsLayout.top, margin = Dp(8f))
                height = Dimension.fillToConstraints
            }
        ) {
            items(listOfNumbers.size - 9) {
                val number = listOfNumbers[it]
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .aspectRatio(1f)
                        .background(
                            color = if (number.isHint) {
                                MaterialTheme.colorScheme.inversePrimary
                            } else if (number.isNumberStillInGame) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                MaterialTheme.colorScheme.tertiaryContainer
                            }
                        )
                        .clickable {
                            gameplayInterface.clickNumber(number)
                        }
                ) {
                    Text(
                        text = number.numberValue.toString(),
                        fontWeight = FontWeight.Bold,
                        color = if (number.isNumberStillInGame) {
                            Color.Black
                        } else {
                            Color.White
                        },
                        fontSize = if (number.isSelected) {
                            26.sp
                        } else {
                            20.sp
                        }
                    )
                }
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(buttonsLayout) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Button(onClick = { gameplayInterface.showHint() }, modifier) {
                GameFragmentBottomMenuButton("Hint")
            }
            Button(onClick = { gameplayInterface.addNumbers(4) }, modifier) {
                GameFragmentBottomMenuButton("Add")
            }
            Button(onClick = { gameOperationsInterface.resetGame() }, modifier) {
                GameFragmentBottomMenuButton("Reset")
            }
        }
    }

}

@Composable
fun GameFragmentBottomMenuButton(text: String) {
    Text(text = text, textAlign = TextAlign.Center, modifier = Modifier)
}

