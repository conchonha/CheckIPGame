package com.sangtb.game.ui.introduce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.databinding.FragmentIntroduceBinding
import com.sangtb.game.ui.introduce.IntroduceViewModel.Companion.LINK_WEB_VIEW
import com.sangtb.game.ui.introduce.IntroduceViewModel.Companion.TYPE
import com.sangtb.game.ui.view.WebViewActivity
import com.sangtb.game.utils.Const.TAG_DIALOG_CODE
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

        viewModel.repositoryImpl.ipAddress.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: $it")
            viewModel.setIpList(it)
        }
        viewModel.apply {
            codeIntroduce.observe(viewLifecycleOwner) {
                DialogGame()
                    .setCanceler()
                    .setTitle(getString(R.string.notification))
                    .setMessage(getString(R.string.introduce_code) + it.code)
                    .show(childFragmentManager, TAG_DIALOG_CODE)
            }
            linkForumXoc.observe(viewLifecycleOwner) {
                showWeb(it.link)
            }
            supportContactNumber.observe(viewLifecycleOwner) {
                showWeb(it.link)
            }
            zaLoNumber.observe(viewLifecycleOwner) {
                showWeb(it.link)
            }
            linkKu.observe(viewLifecycleOwner) {
                loadWebView(it.first, it.second)
            }
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

    fun loadWebView(type: String?, link: String?) {
        val intent = Intent(this.context, WebViewActivity::class.java).apply {
            putExtra(LINK_WEB_VIEW, link)
            putExtra(TYPE, type)
        }
        startActivity(intent)
    }
}