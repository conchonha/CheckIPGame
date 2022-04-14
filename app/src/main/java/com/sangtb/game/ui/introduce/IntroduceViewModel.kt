package com.sangtb.game.ui.introduce

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sangtb.androidlibrary.base.BaseViewModel
import com.sangtb.game.R
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.data.response.IPList
import com.sangtb.game.utils.Const
import com.sangtb.game.utils.SharePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(
    application: Application,
    val repositoryImpl: IpRepositoryImpl,
    val sharePrefs: SharePrefs
) : BaseViewModel(application) {

    private var _ipList: IPList? = null

    fun setIpList(ipList: IPList) {
        _ipList = ipList
    }

    fun checkInterNetVietNam(): Boolean {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone

        return _ipList?.ip == Const.COUNTRY_CODE_VIETNAME && tz.id == Const.TIME_ZONE_VIETNAM
    }
}