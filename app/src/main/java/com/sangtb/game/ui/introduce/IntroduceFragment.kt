package com.sangtb.game.ui.introduce

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.databinding.FragmentIntroduceBinding
import com.sangtb.game.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

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