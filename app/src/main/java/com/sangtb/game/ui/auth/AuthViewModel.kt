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
import com.sangtb.game.data.ApiIpList
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.data.response.ResultGoogleSheet
import com.sangtb.game.di.ApiIPVietNam
import com.sangtb.game.utils.Const
import com.sangtb.game.utils.SharePrefs
import com.sangtb.game.utils.isEmpty1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val ipRepository: IpRepositoryImpl,
    private val sharePrefs: SharePrefs,
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
                val account = Account(
                    userName.value!!,
                    userPhone.value!!,
                    userAccountKu.value!!,
                    userPassword.value!!
                )
//                sharePrefs.saveAccount(account)
                if (it) {
                    checkIpAndWriteGoogleSheet(account)
                } else {
                    _isLogin.postValue(true)
                    evenSender.send(AppEvent.OnShowToast(getString(R.string.register_success)))
                }
            }
        }
    }

    private fun checkIpAndWriteGoogleSheet(account: Account) {
        viewModelScope.launch {
            account.ip = "100000"

            ipRepository.writeGoogleSheetVietNam(account).collect { writeDB ->
                writeDB.onSuccess {value->
                    Log.d(TAG, "checkIpAndWriteGoogleSheet: $value")
                    evenSender.send(
                        AppEvent.OnNavigation(
                            R.id.action_authFragment_to_introduceFragment
                        )
                    )
                }
            }
////            ipRepository.ipAddress.collect {
//                it.onSuccess { ipList ->
//                    evenSender.send(AppEvent.OnShowToast(getString(R.string.register_success)))
//                    Log.d(TAG, "onSuccess getIpList: $ipList: --- account: $account")
////                    if(ipList.countrycode != Const.COUNTRY_CODE_VIETNAME)
//                    account.ip = ipList.ip
//                    ipRepository.writeGoogleSheetVietNam(account).collect { writeDB ->
//                        writeDB.onSuccess {
//                            evenSender.send(
//                                AppEvent.OnNavigation(
//                                    R.id.action_authFragment_to_introduceFragment
//                                )
//                            )
//                        }
//                    }
//                }
//            }
        }
    }

    //    fun postDataGoogleSheets(account: Account) {
//        val dataService: ApiIpList = apiServices
//        val callback: Call<String> = dataService.writeGoogleSheet(account )
//        callback.enqueue(object : Callback<String?> {
//            override fun onResponse(call: Call<String?>, response: Response<String?>) {
//                Log.d("TAG", "onResponse: $response")
//                reponse1.setValue("Success")
//                Log.d("TAG", "onResponse: $response")
//            }
//
//            override fun onFailure(call: Call<String?>, t: Throwable) {
//                Log.d("TAG", "onResponse: err send code google sheet onFailure: $t")
//                if (t.toString()
//                        .startsWith("java.net.UnknownHostException: Unable to resolve host")
//                ) {
//                    reponse1.setValue("Success")
//                } else {
//                    reponse1.setValue("Please check internet of you")
//                }
//            }
//        })
//    }
    fun checkAccount() {
        if (sharePrefs.checkAccount()) {
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