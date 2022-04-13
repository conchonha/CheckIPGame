package com.sangtb.game

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.ui.auth.AuthFragment
import com.sangtb.game.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _navHostController: NavController
    private lateinit var _navHostFragment: NavHostFragment
    private var jog : Job? = null

    @Inject
    lateinit var ipRepository: IpRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jog = lifecycleScope.launchWhenStarted {
            ipRepository.getIpList()
        }

        ipRepository.showToastError.observe(this@MainActivity) {
            showToast(getString(R.string.connect_server_fail) + it.message)
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
               if(it is AuthFragment) {
                   it.setIsLogin()
               }
           }
        }
    }

    override fun onDestroy() {
        jog?.cancel()
        jog = null
        super.onDestroy()
    }

    companion object{
        private const val INDEX_AUTH_FRAGMENT = 0
    }
}