package com.sangtb.game.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sangtb.androidlibrary.base.AppEvent
import com.sangtb.androidlibrary.base.BaseViewModel
import com.sangtb.game.R
import com.sangtb.game.data.Account
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.utils.SharePrefs
import com.sangtb.game.utils.isEmpty1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val ipRepository: IpRepositoryImpl,
    private val sharePrefs: SharePrefs
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

    fun setIsLoginForBack() {
        if (_isLogin.value == false) {
            setIsLogin(true)
        }
    }

    fun setOnNavigate() {
        viewModelScope.launch {
            _isLogin.value?.let {
                if (userName.isEmpty1() && userPhone.isEmpty1() && userAccountKu.isEmpty1() && userPassword.isEmpty1()) {
                    evenSender.send(AppEvent.OnShowToast(getString(R.string.register_not_success)))
                    return@launch
                }
                sharePrefs.saveAccount(
                    Account(
                        userName.value!!,
                        userPhone.value!!,
                        userAccountKu.value!!,
                        userPassword.value!!
                    )
                )
                if (it) {
                    checkIpAndWriteGoogleSheet()
                    evenSender.send(
                        AppEvent.OnNavigation(
                            R.id.action_authFragment_to_introduceFragment
                        )
                    )
                } else {
                    _isLogin.postValue(true)
                    evenSender.send(AppEvent.OnShowToast(getString(R.string.register_success)))
                }
            }
        }
    }

    fun checkIpAndWriteGoogleSheet() {
        Log.d(TAG, "getIpList: ")
        viewModelScope.launch {
            ipRepository.ipAddress.collect {
                it.onSuccess {
                    Log.d(TAG, "onSuccess getIpList: $it:")
                }
                it.onFailure {
                    Log.d(TAG, "onFailure getIpList: ${it.message}")
                }
            }
        }
    }

    fun checkAccount(){
        if(sharePrefs.checkAccount()){
            viewModelScope.launch {
                evenSender.send(
                    AppEvent.OnNavigation(
                        R.id.action_authFragment_to_introduceFragment
                    )
                )
            }
        }
    }

    companion object {
        const val KEY_USER_NAME = "USER_NAME"
    }
}