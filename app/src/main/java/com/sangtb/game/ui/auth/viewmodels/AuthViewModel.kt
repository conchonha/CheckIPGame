package com.sangtb.game.ui.auth.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sangtb.androidlibrary.base.BaseViewModel

class AuthViewModel(application: Application) : BaseViewModel(application) {

    private val _isLogin = MutableLiveData(true)
    val isLogin: LiveData<Boolean>
        get() = _isLogin

    val userName = MutableLiveData("")
    val userPhone = MutableLiveData("")
    val userAccountKu = MutableLiveData("")
    val userPassword = MutableLiveData("")

    fun setIsLogin(isBool: Boolean) {
        _isLogin.postValue(isBool)
    }
}