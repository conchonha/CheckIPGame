package com.sangtb.game.ui.introduce

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sangtb.androidlibrary.base.BaseViewModel
import com.sangtb.game.data.repository.DataFireBaseRepository
import com.sangtb.game.data.repository.DataFireBaseRepositoryImpl
import com.sangtb.game.R
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.data.response.CodeIntroduce
import com.sangtb.game.data.response.Diendanxoc
import com.sangtb.game.data.response.IPList
import com.sangtb.game.data.response.LinkKu
import com.sangtb.game.utils.Const
import com.sangtb.game.utils.SharePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(
    application: Application,
    val repositoryImpl: IpRepositoryImpl,
    val sharePrefs: SharePrefs,
    val dataFireBaseRepository: DataFireBaseRepositoryImpl
) : BaseViewModel(application) {

    private var _ipList: IPList? = null

    private var _linkKu = MutableLiveData<LinkKu>()
    val linkKu: LiveData<LinkKu>
        get() = _linkKu

    private var _linkDiendanxoc = MutableLiveData<Diendanxoc>()
    val linkDiendanxoc: LiveData<Diendanxoc>
        get() = _linkDiendanxoc

    private var _codeIntroduce = MutableLiveData<CodeIntroduce>()
    val codeIntroduce: LiveData<CodeIntroduce>
        get() = _codeIntroduce

    //khi có database sẽ update sau
    private var _supportContactNumber = MutableLiveData<LinkKu>()
    val supportContactNumber: LiveData<LinkKu>
        get() = _supportContactNumber

    private var _zaloNumber = MutableLiveData<LinkKu>()
    val zaloNumber: LiveData<LinkKu>
        get() = _zaloNumber

    fun setIpList(ipList: IPList) {
        _ipList = ipList
    }

    fun checkInterNetVietNam(): Boolean {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone

        return _ipList?.ip == Const.COUNTRY_CODE_VIETNAME && tz.id == Const.TIME_ZONE_VIETNAM
    }

    fun getCodeIntroduces() {
        Log.d(TAG, "getCodeIntroduces: ")
        viewModelScope.launch {
            dataFireBaseRepository.getCodeIntroduce{
                _codeIntroduce.postValue(it[0])
                Log.d(TAG, "getCodeIntroduce: ${it.get(0)}")
            }
        }
    }

    fun getLinkku() {
        Log.d(TAG, "getLinkku: ")
        viewModelScope.launch {
            dataFireBaseRepository.getLinkku{
                _linkKu.postValue(it[0])
                Log.d(TAG, "getLinkku: ${it.get(0)}")
            }
        }
    }

    //khi có data sẽ update sau
    fun getSupportContactNumbers() {
        Log.d(TAG, "getSupportContactNumbers: ")
        viewModelScope.launch {
            dataFireBaseRepository.getLinkku{
                _linkKu.postValue(it[1])
                Log.d(TAG, "getSupportContactNumber: ${it.get(1)}")
            }
        }
    }

    //khi có data sẽ update sau
    fun getZaloNumbers() {
        Log.d(TAG, "getZaloNumbers: ")
        viewModelScope.launch {
            dataFireBaseRepository.getLinkku{
                _linkKu.postValue(it[1])
                Log.d(TAG, "getZaloNumber: ${it.get(1)}")
            }
        }
    }

    fun getLinkDiendanxocs() {
        Log.d(TAG, "getLinkDiendanxocs: ")
        viewModelScope.launch {
            dataFireBaseRepository.getLinkDiendanxoc{
                _linkDiendanxoc.postValue(it[0])
                Log.d(TAG, "getLinkDiendanxoc: ${it.get(0)}")
            }
        }
    }
}