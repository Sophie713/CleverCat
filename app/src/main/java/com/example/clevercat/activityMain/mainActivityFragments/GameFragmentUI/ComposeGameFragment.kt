package com.example.clevercat.activityMain.mainActivityFragments.GameFragmentUI

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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.clevercat.iterfaces.GameOperationsInterface
import com.example.clevercat.iterfaces.GameplayInterface
import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.sharedClasses.errorHandling.DefaultCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ComposeGameFragment(
    modifier: Modifier,
    listOfNumbers: SnapshotStateList<NumberItem>,
    gameOperationsInterface: GameOperationsInterface,
    gameplayInterface: GameplayInterface
) {
    var isLoading by remember { mutableStateOf(true) }


    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (buttonsLayout, grid, loader) = createRefs()
        val state = rememberLazyGridState()
        // Remember a CoroutineScope to be able to launch
        val coroutineScope = rememberCoroutineScope()
        if (isLoading) {
            ComposeLoadingIndicator(modifier = modifier.constrainAs(loader) {
                top.linkTo(parent.top, margin = Dp(48f))
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        }
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(9),
            modifier = modifier.constrainAs(grid) {
                top.linkTo(parent.top, margin = Dp(16f))
                bottom.linkTo(buttonsLayout.top, margin = Dp(8f))
                height = Dimension.fillToConstraints
            }
        ) {
            if (listOfNumbers.size > 9) {
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
            isLoading = false
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(buttonsLayout) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            GameFragmentBottomMenuButton("Hint", {
                coroutineScope.launch(Dispatchers.IO + DefaultCoroutineExceptionHandler(this.javaClass.name) {

                }) {
                    gameplayInterface.showHint()?.let {
                        withContext(Dispatchers.Main) { state.scrollToItem(it) }
                    }
                }
            }, modifier)
            GameFragmentBottomMenuButton("Add", { gameplayInterface.addNumbers(4) }, modifier)
            GameFragmentBottomMenuButton(
                "Reset",
                { gameOperationsInterface.resetGame() },
                modifier
            )
        }
    }

}

