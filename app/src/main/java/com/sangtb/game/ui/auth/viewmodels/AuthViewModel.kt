package com.sangtb.game.ui.auth.viewmodels

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sangtb.androidlibrary.base.AppEvent
import com.sangtb.androidlibrary.base.BaseViewModel
import com.sangtb.game.R
import com.sangtb.game.data.IPList
import com.sangtb.game.data.IpRepository
import com.sangtb.game.data.IpRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val ipRepository: IpRepositoryImpl
) : BaseViewModel(application) {

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
            } else {
                _isLogin.postValue(true)
                // xử lí logic từ register sang login
            }
        }
    }

    fun getIpList() {
        Log.d(TAG, "getIpList: ")
        viewModelScope.launch {
            ipRepository.getIpList().collect {
                it.onSuccess {
                    Log.d(TAG, "onSuccess getIpList: $it:")
                }
                it.onFailure {
                    Log.d(TAG, "onFailure getIpList: ${it.message}")
                }
            }
        }
    }

    companion object {
        const val KEY_USER_NAME = "USER_NAME"
    }
}