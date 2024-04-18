package com.example.clevercat.activityMain.mainActivityFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.clevercat.activityMain.iterfaces.GameOperationsInterface
import com.example.clevercat.activityMain.iterfaces.GameplayInterface
import com.example.clevercat.activityMain.mainActivityFragments.ComposeUI.ComposeGameFragment
import com.example.clevercat.activityMain.viewModels.GameFragmentViewModel
import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.sharedClasses.abstractClasses.BaseFragment
import com.example.clevercat.sharedClasses.constants.GameInitialState
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class GameFragment : BaseFragment() {

    private val viewModel by viewModels<GameFragmentViewModel>()
    private val navArgs: GameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply {
            setContent {
                ComposeGameFragment(
                    modifier = Modifier,
                    listOfNumbers = viewModel.getNumbersArray(),
                    gameOperationsInterface = object : GameOperationsInterface {
                        override fun saveGame(numbersArray: ArrayList<NumberItem>) {
                            viewModel.saveGame()
                        }

                        override fun loadGame() {
                            viewModel.loadGame()
                        }

                        override fun resetGame() {
                            viewModel.resetGame()
                        }

                    },
                    gameplayInterface = object : GameplayInterface {
                        override fun showHint() {
                            viewModel.showHint()
                        }

                        override fun addNumbers(numberOfRows: Int) {
                            viewModel.addNumbers(numberOfRows)
                        }

                        override fun clickNumber(number: NumberItem) {
                            viewModel.onNumberClick(number)
                        }

                    },
                )
            }
        }
        //layoutInflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //load or start a new game todo xyz implement loader
        viewModel.viewModelEvents.observe(viewLifecycleOwner, {
            when (it) {
                GameFragmentViewModel.ViewModelEvents.NO_MATCH_FOUND -> {
                    Toast.makeText(requireContext(), "No more matches.", Toast.LENGTH_SHORT).show()
                }

                GameFragmentViewModel.ViewModelEvents.LOAD_GAME_FAILED -> {
                    Toast.makeText(requireContext(), "No saved game found.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        when (navArgs.GameInitialState) {
            GameInitialState.NEW_GAME -> {
                viewModel.resetGame()
            }

            GameInitialState.LOAD_GAME -> {
                viewModel.loadGame()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveGame()
    }

}
