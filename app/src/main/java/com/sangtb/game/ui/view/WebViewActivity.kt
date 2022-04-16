package com.sangtb.game.ui.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.sangtb.androidlibrary.base.BaseActivity
import com.sangtb.androidlibrary.base.BaseFragment
import com.sangtb.game.R
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.databinding.ActivityWebViewBinding
import com.sangtb.game.ui.introduce.IntroduceViewModel.Companion.LINK_WEB_VIEW
import com.sangtb.game.ui.introduce.IntroduceViewModel.Companion.TYPE
import com.sangtb.game.utils.Types
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {

    private var linkWebView: String? = ""
    private var type: String? = ""

    @Inject
    lateinit var ipRepository: IpRepositoryImpl
    lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_web_view, null, false)
        setContentView(binding.root)
        intent.apply {
            linkWebView = getStringExtra(LINK_WEB_VIEW)
            type = getStringExtra(TYPE)
        }
        loadWebView()
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linkWebView = arguments?.getString(LINK_WEB_VIEW)
        type = arguments?.getString(TYPE)
        Log.d(TAG, "onViewCreated: NAMTD8 type -- $type")
        Log.d(TAG, "onViewCreated: NAMTD8 link-- $linkWebView")
        loadWebView()
    }
*/
    /*fun onKeyDow(keyCode: Int, event: KeyEvent?) : Boolean {
        Log.d(TAG, "onKeyDow: cangoback")
        if (event!!.action === KeyEvent.ACTION_DOWN) {
            Log.d(TAG, "onKeyDow: ACTION_DOWN")
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    Log.d(TAG, "onKeyDow: KEYCODE_BACK")
                    binding.webView.apply {
                        if (canGoBack()) {
                            Log.d(TAG, "onKeyDow: cangoback")
                            binding.webView.goBack()
                            return true
                        }else {
                            return false
                        }
                    }
                }
            }
        }
        return false
    }*/

    private fun loadWebView() {
        Log.d("TAG", "loadWebView: ")
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadsImagesAutomatically = true
            allowContentAccess = true
            setSupportMultipleWindows(false)
            allowFileAccess = true
            loadWithOverviewMode = true
            useWideViewPort = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            pluginState = WebSettings.PluginState.ON
        }
        binding.webView.apply {
            if (type!! == Types.TYPE_REGISTER.name) {
                //clear cache and cookies
                clearHistory()
                clearFormData()
                clearCache(true)
                Log.d("TAG", "loadWebView  111: $linkWebView")
            }
            linkWebView?.let {
                loadUrl(it)
                Log.d("TAG", "loadWebView: $it")
            }
            webViewClient = object : WebViewClient() {
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    val builder = AlertDialog.Builder(context)
                    var message = "SSL Certificate error."
                    when (error?.primaryError) {
                        SslError.SSL_UNTRUSTED -> message =
                            "The certificate authority is trusted."
                        SslError.SSL_EXPIRED -> message = "The certificate has expired."
                        SslError.SSL_IDMISMATCH -> message =
                            "The certificate Hostname mismatch."
                        SslError.SSL_NOTYETVALID -> message =
                            "The certificate is not yet valid."
                    }
                    message += "Do you want to continue anyway?"
                    builder.apply {
                        setTitle("SSL Certificate Error")
                        setMessage(message)
                        setPositiveButton("continue") { _, _ ->
                            handler?.proceed()
                        }
                        setNegativeButton("cancel") { _, _ ->
                            handler?.cancel()
                        }
                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    Log.d("TAG", "onPageStarted: ")
                    if (type!!.equals(Types.TYPE_REGISTER.name) && url!!.contains("/Mobile/Register/Register")) {
                        //chờ khách trả lời
                        /*val referalCode = ShareRefence.REFERRAL_CODE
                        if (referalCode.equals("")){
                            referalCode = "FF3632"
                        }*/
                        /* view?.loadUrl("javascript:(function(){window.localStorage.setItem('AgentID'," +referralCode + "');})()")*/
                    }
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.d("TAG", "onPageFinished: ")
                    //  ipRepository.onShowDialog()
                    if (type!!.equals(Types.TYPE_REGISTER.name) && url!!.contains("/Mobile/Register/Register")) {
                        val jsDisabledReferral =
                            "window.document.getElementById('AgentID').setAttribute('disabled'), 'true');"
                        val jsColorReferral =
                            "document.getElementById('AgentID').style.color ='white';"
                        view?.let {
                            it.loadUrl("javascript:document.getElementsByClassName('btn_ADclose')[0].click()")
                            // Nick Name
                            it.loadUrl("javascript:(function(){" + jsDisabledReferral + jsColorReferral + "})()");
                        }
                    } else if (type!!.equals(Types.TYPE_LOGIN.name)) {
                        view?.let {
                            it.loadUrl("javascript:document.getElementsByClassName('btn_ADclose')[0].click()");
                            it.loadUrl("javascript:document.getElementsByClassName('btn_homeLogin')[0].click()");
                        }
                    }
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("TAG", "onKeyDown: ${event?.action == KeyEvent.ACTION_DOWN} ")
        if (event?.action == KeyEvent.ACTION_DOWN) {
            Log.d("TAG", "onKeyDown: 222")
            when (keyCode) {
                KeyEvent.KEYCODE_BACK ->{
                    Log.d("TAG", "onKeyDown:333 ")
                    binding.webView.apply {
                        if (canGoBack()) {
                            Log.d("TAG", "onKeyDown: 111")
                            goBack()
                        } else {
                            Log.d("TAG", "onKeyDown: 44")
                            finish()
                        }
                    }
                    return true 
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}