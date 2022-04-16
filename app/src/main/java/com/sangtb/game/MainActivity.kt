package com.sangtb.game

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sangtb.game.base.BaseActivity
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.ui.auth.AuthFragment
import com.sangtb.game.utils.Helpers
import com.sangtb.game.utils.showToast
import dagger.hilt.android.AndroidEntryPoint



import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var _navHostController: NavController
    private lateinit var _navHostFragment: NavHostFragment

    @Inject
    lateinit var ipRepository: IpRepositoryImpl

    @Inject
    lateinit var helpers: Helpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenStarted {
            ipRepository.getIpList {
                hideDialog()
            }
        }

        ipRepository.showDialogLoading.observe(this) {
            Log.d(TAG, "onCreate: $it")
            if (it) showProgressDialog() else hideDialog()
        }

        ipRepository.showToastError.observe(this@MainActivity) {
            if (!helpers.isInternetAvailable(this)) {
                showToast(getString(R.string.canot_connect_internet))
            } else {
                showToast(getString(R.string.connect_server_fail) + it.message)
            }
        }

        _navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        _navHostController = _navHostFragment.navController
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackStack()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    fun refreshCurrentFragment() {
        val id = _navHostController.currentDestination?.id
        _navHostController.popBackStack(id!!, true)
        _navHostController.navigate(id)
    }

    fun onNavigate(actionId: Int, bundle: Bundle? = null) {
        _navHostFragment.findNavController().navigate(actionId, bundle)
    }

    fun onBackStack() {
        if (_navHostController.currentDestination?.id != R.id.authFragment) {
            _navHostController.popBackStack()
        } else {
            _navHostFragment.childFragmentManager.fragments[INDEX_AUTH_FRAGMENT]?.let {
                if (it is AuthFragment) {
                    it.setIsLogin()
                }
            }
        }
    }

    companion object {
        private const val INDEX_AUTH_FRAGMENT = 0
    }
}