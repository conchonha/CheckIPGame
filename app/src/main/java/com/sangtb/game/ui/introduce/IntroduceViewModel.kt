package com.sangtb.game.ui.introduce

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sangtb.androidlibrary.base.AppEvent
import com.sangtb.androidlibrary.base.BaseViewModel
import com.sangtb.game.data.repository.DataFireBaseRepositoryImpl
import com.sangtb.game.R
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.data.response.CodeIntroduce
import com.sangtb.game.data.response.Diendanxoc
import com.sangtb.game.data.response.IPList
import com.sangtb.game.data.response.LinkKu
import com.sangtb.game.utils.Const
import com.sangtb.game.utils.SharePrefs
import com.sangtb.game.utils.SingleLiveEvent1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(
    application: Application,
    val repositoryImpl: IpRepositoryImpl,
    val sharePrefs: SharePrefs,
    private val dataFireBaseRepository: DataFireBaseRepositoryImpl
) : BaseViewModel(application) {

    private var _ipList: IPList? = null

    private val _linkKu = MutableLiveData<LinkKu>()
    val linkKu: LiveData<LinkKu>
        get() = _linkKu

    private val _linkDiendanxoc = MutableLiveData<Diendanxoc>()
    val linkDiendanxoc: LiveData<Diendanxoc>
        get() = _linkDiendanxoc

    val codeIntroduce = SingleLiveEvent1<CodeIntroduce>()

    //khi có database sẽ update sau
    private val _supportContactNumber = MutableLiveData<LinkKu>()
    val supportContactNumber: LiveData<LinkKu>
        get() = _supportContactNumber

    private val _zaloNumber = MutableLiveData<LinkKu>()
    val zaloNumber: LiveData<LinkKu>
        get() = _zaloNumber

    fun setIpList(ipList: IPList) {
        _ipList = ipList
    }

    private fun checkInterNetVietNam(): Boolean {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone

        val boolean = _ipList?.countrycode == Const.COUNTRY_CODE_VIETNAME && tz.id == Const.TIME_ZONE_VIETNAM
        Log.d(TAG, "checkInterNetVietNam: $boolean")
        return boolean
    }

    fun getCodeIntroduces() {
        Log.d(TAG, "getCodeIntroduces: ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()){
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getCodeIntroduce {
                    codeIntroduce.postValue(it[0])
                    Log.d(TAG, "getCodeIntroduce: ${it.get(0)}")
                }
            }
        }
    }

    fun getLinkku() {
        Log.d(TAG, "getLinkku:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()){
            repositoryImpl.onShowDialog(true)
            dataFireBaseRepository.getLinkku {
                viewModelScope.launch {
                    evenSender.send(
                        AppEvent.OnNavigation(
                            R.id.action_introduceFragment_to_webViewFragment,
                            bundle = Bundle().apply {
                                putString(LINK_WEB_VIEW, it[0].link)
                            }
                        )
                    )
                    Log.d(TAG, "getLinkku: ${it.get(0)}")
                }
            }
        }
    }

    //khi có data sẽ update sau
    fun getSupportContactNumbers() {
        Log.d(TAG, "getSupportContactNumbers:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()){
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getLinkku {
                    _linkKu.postValue(it[1])
                    Log.d(TAG, "getSupportContactNumber: ${it.get(1)}")
                }
            }
        }
    }

    //khi có data sẽ update sau
    fun getZaloNumbers() {
        Log.d(TAG, "getZaloNumbers:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()){
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getLinkku {
                    _linkKu.postValue(it[1])
                    Log.d(TAG, "getZaloNumber: ${it.get(1)}")
                }
            }
        }
    }

    fun getLinkDiendanxocs() {
        Log.d(TAG, "getLinkDiendanxocs: ")
        if (checkInterNetVietNam()){
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getLinkDiendanxoc {
                    _linkDiendanxoc.postValue(it[0])
                    Log.d(TAG, "getLinkDiendanxoc: ${it.get(0)}")
                }
            }
        }
    }

    companion object {
        const val LINK_WEB_VIEW = "LinkWebView"
    }
}