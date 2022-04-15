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
import com.sangtb.game.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
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

    val codeIntroduce = SingleLiveEvent<CodeIntroduce>()

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

        val boolean =
            _ipList?.countrycode == Const.COUNTRY_CODE_VIETNAME && tz.id == Const.TIME_ZONE_VIETNAM
        Log.d(TAG, "country: ${_ipList?.countrycode} --- TimeZone: ${tz.id}")
        return boolean
    }

    fun getCodeIntroduces() {
        Log.d(TAG, "getCodeIntroduces: ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()) {
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getCodeIntroduce {
                    codeIntroduce.postValue(it[INDEX_0])
                    Log.d(TAG, "getCodeIntroduce: ${it[INDEX_0]}")
                }
            }
        }
    }

    fun getLinkDkKu() {
        Log.d(TAG, "getLinkKu:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam())
            dataFireBaseRepository.getLinkDkku {
                repositoryImpl.onShowDialog(true)
                viewModelScope.launch {
                    evenSender.send(
                        AppEvent.OnNavigation(
                            R.id.action_introduceFragment_to_webViewFragment,
                            bundle = Bundle().apply {
                                putString(LINK_WEB_VIEW, it[0].link)
                            }
                        )
                    )
                    Log.d(TAG, "getLinkKu: ${it[INDEX_0]}")
                }
            }
    }

    fun getLinkDnKu() {
        Log.d(TAG, "getLinkKu:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam())
            dataFireBaseRepository.getLinkDnku {
                repositoryImpl.onShowDialog(true)
                viewModelScope.launch {
                    evenSender.send(
                        AppEvent.OnNavigation(
                            R.id.action_introduceFragment_to_webViewFragment,
                            bundle = Bundle().apply {
                                putString(LINK_WEB_VIEW, it[INDEX_0].link)
                            }
                        )
                    )
                    Log.d(TAG, "getLinkKu: ${it.get(INDEX_0)}")
                }
            }
    }

    //khi có data sẽ update sau
    fun getSupportContactNumbers() {
        Log.d(TAG, "getSupportContactNumbers:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()) {
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getLinkDkku {
                    _linkKu.postValue(it[1])
                    Log.d(TAG, "getSupportContactNumber: ${it[INDEX_1]}")
                    dataFireBaseRepository.getLinkDnku { result ->
                        _supportContactNumber.postValue(result[INDEX_0])
                        Log.d(TAG, "getSupportContactNumber: ${result[INDEX_0]}")
                    }
                }
            }
        }
    }

    //khi có data sẽ update sau
    fun getZaLoNumbers() {
        Log.d(TAG, "getZaLoNumbers:  ${checkInterNetVietNam()}")
        if (checkInterNetVietNam()) {
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getLinkDkku {
                    _zaloNumber.postValue(it[INDEX_0])
                    Log.d(TAG, "getZaLoNumber: ${it[INDEX_0]}")
                    dataFireBaseRepository.getLinkDkku {result->
                        _linkKu.postValue(result[INDEX_1])
                        Log.d(TAG, "getZaLoNumber: ${result[INDEX_1]}")
                    }
                }
            }
        }
    }

    fun getLinkDiendanxocs() {
        Log.d(TAG, "getLinkDiendanxocs: ")
        if (checkInterNetVietNam()) {
            repositoryImpl.onShowDialog(true)
            viewModelScope.launch {
                dataFireBaseRepository.getLinkDiendanxoc {
                    _linkDiendanxoc.postValue(it[INDEX_0])
                    Log.d(TAG, "getLinkDiendanxoc: ${it[INDEX_0]}")
                }
            }
        }
    }

    companion object {
        const val LINK_WEB_VIEW = "LinkWebView"
        private const val INDEX_0 = 0
        private const val INDEX_1 = 1
    }
}