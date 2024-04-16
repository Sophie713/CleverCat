package com.example.clevercat.activityMain.mainActivityFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.clevercat.R
import com.example.clevercat.databinding.FragmentMainMenuBinding
import com.example.clevercat.sharedClasses.abstractClasses.BaseFragment
import com.example.clevercat.sharedClasses.constants.GameInitialState
import com.example.clevercat.sharedClasses.extentions.safeNavigate
import com.example.clevercat.sharedClasses.extentions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MainMenuFragment : BaseFragment() {

    private val binding by viewBinding(FragmentMainMenuBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentMainMenuNewGame.setOnClickListener {
            findNavController().safeNavigate(MainMenuFragmentDirections.actionMainMenuFragmentToGameFragment(
                GameInitialState.NEW_GAME
            ))
        }
        binding.fragmentMainMenuLoadGame.setOnClickListener {
            findNavController().safeNavigate(MainMenuFragmentDirections.actionMainMenuFragmentToGameFragment(
                GameInitialState.LOAD_GAME
            ))
        }
    }
}