package com.sangtb.game.ui.auth.viewmodels

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sangtb.androidlibrary.base.AppEvent
import com.sangtb.androidlibrary.base.BaseViewModel
import com.sangtb.game.R
import kotlinx.coroutines.launch

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

    fun setOnNavigate() {
        _isLogin.value?.let {
            if (it) {
                viewModelScope.launch {
                    userName.value?.let {
                        evenSender.send(
                            AppEvent.OnNavigation(
                                R.id.action_authFragment_to_introduceFragment,
                                bundle = Bundle().apply {
                                    putString(KEY_USER_NAME, it)
                                })
                        )
                    }
                }
            }
            else {
                _isLogin.postValue(true)
                // xử lí logic từ register sang login
            }
        }
    }

    companion object {
        const val KEY_USER_NAME = "USER_NAME"
    }
}