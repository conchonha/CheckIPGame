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
import com.sangtb.game.utils.Helpers
import com.sangtb.game.utils.SharePrefs
import com.sangtb.game.utils.SingleLiveEvent
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

    private var _ipList: IPList? = null

    private val _linkKu = MutableLiveData<LinkKu>()
    val linkKu: LiveData<LinkKu> = _linkKu

    private val _linkDiendanxoc = MutableLiveData<Diendanxoc>()
    val linkDiendanxoc: LiveData<Diendanxoc> = _linkDiendanxoc

    val codeIntroduce = SingleLiveEvent<CodeIntroduce>()

    //khi có database sẽ update sau
    private val _supportContactNumber = MutableLiveData<LinkKu>()
    val supportContactNumber: LiveData<LinkKu> = _supportContactNumber

    private val _zaloNumber = MutableLiveData<LinkKu>()
    val zaloNumber: LiveData<LinkKu> = _zaloNumber

    fun setIpList(ipList: IPList) {
        _ipList = ipList
    }

    private fun checkInterNetVietNam(action: () -> Unit) {
        if (helpers.checkInterNetVN(_ipList?.countrycode))
            action.invoke()
        else
            viewModelScope.launch {
                evenSender.send(AppEvent.OnShowToast(getString(R.string.please_check_country_internet)))
            }
    }

    fun getCodeIntroduces() = checkInterNetVietNam {
        repositoryImpl.onShowDialog(true)
        viewModelScope.launch {
            dbFirebase.getCodeIntroduce {
                codeIntroduce.postValue(it[INDEX_0])
                Log.d(TAG, "getCodeIntroduce: ${it[INDEX_0]}")
            }
        }
    }

    fun getLinkDkKu() = checkInterNetVietNam {
        dbFirebase.getLinkDkku {
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

    fun getLinkDnKu() = checkInterNetVietNam {
        dbFirebase.getLinkDnku {
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
    fun getSupportContactNumbers() = checkInterNetVietNam {
        repositoryImpl.onShowDialog(true)
        viewModelScope.launch {
            dbFirebase.getLinkDkku {
                _linkKu.postValue(it[1])
                Log.d(TAG, "getSupportContactNumber: ${it[INDEX_1]}")
                dbFirebase.getLinkDnku { result ->
                    _supportContactNumber.postValue(result[INDEX_0])
                    Log.d(TAG, "getSupportContactNumber: ${result[INDEX_0]}")
                }
            }
        }
    }

    //khi có data sẽ update sau
    fun getZaLoNumbers() = checkInterNetVietNam {
        repositoryImpl.onShowDialog(true)
        viewModelScope.launch {
            dbFirebase.getLinkDkku {
                _zaloNumber.postValue(it[INDEX_0])
                Log.d(TAG, "getZaLoNumber: ${it[INDEX_0]}")
                dbFirebase.getLinkDkku { result ->
                    _linkKu.postValue(result[INDEX_1])
                    Log.d(TAG, "getZaLoNumber: ${result[INDEX_1]}")
                }
            }
        }
    }

    fun getLinkDiendanxocs() = checkInterNetVietNam {
        repositoryImpl.onShowDialog(true)
        viewModelScope.launch {
            dbFirebase.getLinkDiendanxoc {
                _linkDiendanxoc.postValue(it[INDEX_0])
                Log.d(TAG, "getLinkDiendanxoc: ${it[INDEX_0]}")
            }
        }
    }

    companion object {
        const val LINK_WEB_VIEW = "LinkWebView"
        private const val INDEX_0 = 0
        private const val INDEX_1 = 1
    }
}