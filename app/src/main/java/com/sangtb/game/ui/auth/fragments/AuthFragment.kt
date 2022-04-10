package com.sangtb.game.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.ui.auth.viewmodels.AuthViewModel
import com.sangtb.game.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding, AuthViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_auth
    override val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.action = viewModel
    }
}