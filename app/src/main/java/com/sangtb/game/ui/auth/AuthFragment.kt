package com.sangtb.game.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding, AuthViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_auth

    override val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var repositoryImpl: IpRepositoryImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkAccount()
        binding.action = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            repositoryImpl.ipAddress.collect {
                it.onSuccess { viewModel.setResultAddress(it) }
            }
        }
    }

    override fun navigateToDestination(destination: Int, bundle: Bundle?) {
        findNavController().navigate(destination)
    }

    fun setIsLogin() {
        viewModel.setIsLoginForBack()
    }
}