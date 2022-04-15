package com.sangtb.game.ui.introduce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.databinding.FragmentIntroduceBinding
import com.sangtb.game.ui.auth.AuthViewModel
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
            showWeb(it.link)
        }

        viewModel.supportContactNumber.observe(viewLifecycleOwner) {
            showWeb(it.link)
        }
        viewModel.zaloNumber.observe(viewLifecycleOwner) {
            showWeb(it.link)
        }
        binding.btnGetReferralCode.setOnClickListener {
            DialogGame().show(childFragmentManager, "SANG")
        }
    }

    override fun navigateToDestination(destination: Int, bundle: Bundle?) {
        findNavController().navigate(destination, bundle)
    }

    private fun showWeb(uri: String) {
        val mUri = Uri.parse(uri)
        val intent = Intent(Intent.ACTION_VIEW, mUri)
        context?.startActivity(intent)
    }
}