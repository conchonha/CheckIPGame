package com.sangtb.game.data.repository

import android.util.Log
import com.sangtb.androidlibrary.utils.SingleLiveEvent
import com.sangtb.game.data.Account
import com.sangtb.game.data.ApiIpList
import com.sangtb.game.data.response.IPList
import com.sangtb.game.di.ApiIPAddress
import com.sangtb.game.di.ApiIPVietNam
import com.sangtb.game.utils.SingleLiveEvent1
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class IpRepositoryImpl @Inject constructor(
    @ApiIPAddress private val apiIpList: ApiIpList,
    @ApiIPVietNam private val apiIPVietNam: ApiIpList
) : IpRepository() {
    val showDialogLoading = SingleLiveEvent1<Boolean>()

    val showToastError = SingleLiveEvent1<Throwable>()

    private val _ipAddress = MutableSharedFlow<Result<IPList>>()
    val ipAddress = _ipAddress.asSharedFlow()

    override val repository: IpRepositoryImpl
        get() = this

    override suspend fun getIpList() {
        val response = safeApiCall { apiIpList.getAPIIp() }
        _ipAddress.emit(response)
    }

    override suspend fun writeGoogleSheetVietNam(account: Account): Flow<Result<Any>> {
        return flow {
            val response = safeApiCall {
                apiIPVietNam.writeGoogleSheet(
                    account.ip ?: "",
                    account.name,
                    account.phone,
                    account.accountKU,
                    account.password,
                    account.action
                )
            }
            emit(response)
        }
    }

    override fun onFail(throwable: Throwable) {
        showToastError.postValue(throwable)
    }

    override fun onShowDialog(boolean: Boolean?) {
        Log.d("SangTB", "onShowDialog: $boolean")
        showDialogLoading.postValue(boolean ?: false)
    }
}