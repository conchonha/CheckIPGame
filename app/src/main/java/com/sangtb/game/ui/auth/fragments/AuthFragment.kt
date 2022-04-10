package com.sangtb.game.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.ui.auth.viewmodels.AuthViewModel
import com.sangtb.game.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding, AuthViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_auth
    override val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.action = viewModel
    }

    override fun navigateToDestination(destination: Int, bundle: Bundle?) {
        super.navigateToDestination(destination, bundle)
        findNavController().navigate(destination)
    }
}