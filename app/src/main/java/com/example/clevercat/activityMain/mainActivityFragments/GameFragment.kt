package com.example.clevercat.activityMain.mainActivityFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clevercat.R
import com.example.clevercat.activityMain.adapters.NumbersAdapter
import com.example.clevercat.activityMain.iterfaces.GameOperationsInterface
import com.example.clevercat.activityMain.viewModels.GameFragmentViewModel
import com.example.clevercat.app.prefs
import com.example.clevercat.dataClasses.NumberItem
import com.example.clevercat.databinding.FragmentGameBinding
import com.example.clevercat.sharedClasses.abstractClasses.BaseFragment
import com.example.clevercat.sharedClasses.constants.Constants.LOG_TAG
import com.example.clevercat.sharedClasses.constants.GameInitialState
import com.example.clevercat.sharedClasses.extentions.viewBinding
import com.sophie.miller.clevercat.listener.NumbersUpdatesListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class GameFragment : BaseFragment(), GameOperationsInterface {


    private val viewModel by viewModels<GameFragmentViewModel>()
    private val navArgs: GameFragmentArgs by navArgs()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding by viewBinding(FragmentGameBinding::bind)

    private lateinit var numbersAdapter: NumbersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return layoutInflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //load or start a new game todo xyz implement loader

        numbersAdapter = NumbersAdapter(requireContext(), object : NumbersUpdatesListener {
            override fun scrollToPosition(position: Int) {
                binding.numbersGrid.scrollToPosition(position)
            }

            override fun updateNumber(numberItem: NumberItem) {
                viewModel.updateNumber(numberItem)
            }

            override fun removeNumber(index: Int) {
                viewModel.removeNumber(index)
            }
        })

        //set up recyclerview
        val layoutManager = GridLayoutManager(requireContext(), 9)
        binding.numbersGrid.setLayoutManager(layoutManager)
        binding.numbersGrid.itemAnimator = null
        binding.numbersGrid.adapter = numbersAdapter
        //fill adapter with proper numbers
        when (navArgs.GameInitialState) {
            GameInitialState.NEW_GAME -> {
                prefs.latestId = 0
                addNumbers(10)
            }

            GameInitialState.LOAD_GAME -> {
                loadGame()
            }
        }

        //set up bottom menu
        binding.gameFragmentResetButton.setOnClickListener {
            resetGame()
        }
        binding.gameFragmentAddButton.setOnClickListener {
            addNumbers(8)
        }
        binding.gameFragmentHintButton.setOnClickListener {
            showHint()
        }
    }

    private fun addNumbers(numberOfNewLines: Int) {
        viewModel.addNumbers(numberOfNewLines)//todo get params and load or create
        numbersAdapter.notifyDataSetChanged(viewModel.getNumbersArrayFull())
    }

    override fun onPause() {
        super.onPause()
        saveGame(viewModel.getNumbersArrayFull())
    }

    /**
     * save game automatically so the user can start where they left off
     */
    override fun saveGame(numbersArray: ArrayList<NumberItem>) {
        viewModel.saveGame()
    }

    /**
     * load game if user selected that option
     */
    override fun loadGame() {
        try {
            viewModel.loadGame()
        } catch (ignored: java.lang.Exception) {
            Log.e(LOG_TAG, "" + ignored + "" + ignored.message)
            viewModel.resetGame()
        }
        numbersAdapter.notifyDataSetChanged(viewModel.getNumbersArrayFull())
    }

    /**
     * resetGame
     */
    override fun resetGame() {
        viewModel.resetGame()
        numbersAdapter.notifyDataSetChanged(viewModel.getNumbersArrayFull())
    }

    override fun showHint() {
        numbersAdapter.showHint()
    }

}
