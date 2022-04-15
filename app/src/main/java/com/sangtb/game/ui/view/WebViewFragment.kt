package com.sangtb.game.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.databinding.FragmentWebViewBinding
import com.sangtb.game.ui.introduce.IntroduceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebViewBinding, WebViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_web_view
    override val viewModel: WebViewModel by viewModels()
    private val linkWebView by lazy {
        arguments?.getString(IntroduceViewModel.LINK_WEB_VIEW)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linkWebView?.let {
            Log.d(TAG, "onViewCreated: link webview $it")
            binding.webView.loadUrl(it)
        }
    }


}