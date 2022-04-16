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
import com.sangtb.game.ui.view.WebViewActivity
import com.sangtb.game.utils.Helpers
import com.sangtb.game.utils.SharePrefs
import com.sangtb.game.utils.SingleLiveEvent
import com.sangtb.game.utils.Types
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(
    application: Application,
    val repositoryImpl: IpRepositoryImpl,
    val sharePrefs: SharePrefs,
    private val dbFirebase: DataFireBaseRepositoryImpl,
    private val helpers: Helpers
) : BaseViewModel(application) {

    private val _linkKu = MutableLiveData<Pair<String?, String?>>()
    val linkKu: LiveData<Pair<String?, String?>> = _linkKu

    private val _linkForumXoc = MutableLiveData<Diendanxoc>()
    val linkForumXoc: LiveData<Diendanxoc> = _linkForumXoc

    val codeIntroduce = SingleLiveEvent<CodeIntroduce>()

    //khi có database sẽ update sau
    private val _supportContactNumber = MutableLiveData<LinkKu>()
    val supportContactNumber: LiveData<LinkKu> = _supportContactNumber

    private val _zaLoNumber = MutableLiveData<LinkKu>()
    val zaLoNumber: LiveData<LinkKu> = _zaLoNumber

    private var _ipList: IPList? = null

    fun setIpList(ipList: IPList) {
        _ipList = ipList
    }

    private fun checkInterNetVietNam(action: () -> Unit) {
        viewModelScope.launch {
            when {
                !helpers.isInternetAvailable(getApplication()) -> evenSender.send(
                    AppEvent.OnShowToast(
                        getString(R.string.canot_connect_internet)
                    )
                )
                helpers.checkInterNetVN(_ipList?.countrycode) -> action.invoke()
                else -> evenSender.send(AppEvent.OnShowToast(getString(R.string.please_check_country_internet)))
            }
        }

    }

    fun getCodeIntroduces() = checkInterNetVietNam {
        viewModelScope.launch {
            dbFirebase.getCodeIntroduce {
                it.getOrNull(INDEX_0)?.let { value -> codeIntroduce.postValue(value) }
                Log.d(TAG, "getCodeIntroduce: ${it.getOrNull(INDEX_0)}")
                return@getCodeIntroduce
            }
        }
    }

    fun getLinkDkKu() = checkInterNetVietNam {
        dbFirebase.getLinkDkku {
            Log.d(TAG, "getLinkKu: ${it.getOrNull(INDEX_0)?.link}")
            _linkKu.postValue(Pair(Types.TYPE_REGISTER.name, it.getOrNull(INDEX_0)?.link))
            return@getLinkDkku
        }
    }

    fun getLinkDnKu() = checkInterNetVietNam {
        dbFirebase.getLinkDnku {
            Log.d(TAG, "getLinkDnKu: ${it.getOrNull(INDEX_0)?.link}")
            _linkKu.postValue(Pair(Types.TYPE_LOGIN.name, it.getOrNull(INDEX_0)?.link))
            return@getLinkDnku
        }
    }

    //khi có data sẽ update sau
    fun getSupportContactNumbers() = checkInterNetVietNam {
        viewModelScope.launch {
            dbFirebase.getLinkDnku { result ->
                result.getOrNull(INDEX_0)
                    ?.let { value -> _supportContactNumber.postValue(value) }
                Log.d(TAG, "getSupportContactNumber: ${result.getOrNull(INDEX_0)}")
                return@getLinkDnku
            }
        }
    }

    //khi có data sẽ update sau
    fun getZaLoNumbers() = checkInterNetVietNam {
        viewModelScope.launch {
            dbFirebase.getLinkDkku {
                it.getOrNull(INDEX_0)?.let { value -> _zaLoNumber.postValue(value) }
                Log.d(TAG, "getZaLoNumber: ${it.getOrNull(INDEX_0)}")
                return@getLinkDkku
            }
        }
    }

    fun getLinkForumXoc1() = checkInterNetVietNam {
        viewModelScope.launch {
            dbFirebase.getLinkDiendanxoc {
                it.getOrNull(INDEX_0)?.let { value -> _linkForumXoc.postValue(value) }
                Log.d(TAG, "getLinkDienDanXoc: ${it.getOrNull(INDEX_0)}")
                return@getLinkDiendanxoc
            }
        }
    }

    companion object {
        const val LINK_WEB_VIEW = "LinkWebView"
        const val TYPE = "Type"
        private const val INDEX_0 = 0
        private const val INDEX_1 = 1
    }
}