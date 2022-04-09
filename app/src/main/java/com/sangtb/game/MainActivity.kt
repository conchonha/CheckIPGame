package com.sangtb.game

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    private lateinit var _navHostController: NavController
    private lateinit var _navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
//        if (_navHostController.currentDestination?.id != R.id.splashFragment) {
//            _navHostController.popBackStack()
//        }
    }
}