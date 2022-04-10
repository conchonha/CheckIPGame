package com.sangtb.game.ui.introduce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.databinding.FragmentIntroduceBinding
import com.sangtb.game.ui.auth.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class IntroduceFragment : BaseFragment<FragmentIntroduceBinding, IntroduceViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_introduce
    override val viewModel: IntroduceViewModel by viewModels()
    private val userName  by lazy { arguments?.getString(AuthViewModel.KEY_USER_NAME) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName?.let {
            viewModel.setUserName(it)
        }
    }
}