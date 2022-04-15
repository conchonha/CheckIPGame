package com.sangtb.game.ui.introduce

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.databinding.FragmentIntroduceBinding
import com.sangtb.game.utils.DialogGame
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroduceFragment : BaseFragment<FragmentIntroduceBinding, IntroduceViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_introduce

    override val viewModel: IntroduceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.action = viewModel

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.repositoryImpl.ipAddress.collect {
                it.onSuccess { ipList ->
                    viewModel.setIpList(ipList)
                }
            }
        }

        viewModel.codeIntroduce.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated -- showdialog: $it")
            DialogGame().setTitle("Thông báo").setMessage("Mã giới thiệu của bạn là: ${it.code}")
                .show(childFragmentManager, "SANG")
        }

        viewModel.linkDiendanxoc.observe(viewLifecycleOwner) {

        }
    }

    override fun navigateToDestination(destination: Int, bundle: Bundle?) {
        findNavController().navigate(destination, bundle)
    }
}