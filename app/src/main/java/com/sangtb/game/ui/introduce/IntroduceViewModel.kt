package com.sangtb.game.ui.introduce

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sangtb.androidlibrary.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class IntroduceViewModel(application: Application) : BaseViewModel(application) {

    private val _userName = MutableLiveData("")
    val userName: LiveData<String>
        get() = _userName

    fun setUserName(name: String) {
        _userName.postValue(name)
    }
}