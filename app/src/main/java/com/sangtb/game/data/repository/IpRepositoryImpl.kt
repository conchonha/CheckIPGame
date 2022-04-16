package com.sangtb.game.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sangtb.game.data.Account
import com.sangtb.game.data.ApiIpList
import com.sangtb.game.data.response.IPList
import com.sangtb.game.di.ApiIPAddress
import com.sangtb.game.di.ApiIPVietNam
import com.sangtb.game.utils.SingleLiveEvent
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class IpRepositoryImpl @Inject constructor(
    @ApiIPAddress private val apiIpList: ApiIpList,
    @ApiIPVietNam private val apiIPVietNam: ApiIpList
) : IpRepository() {
    val showDialogLoading = SingleLiveEvent<Boolean>()

    val showToastError = SingleLiveEvent<Throwable>()

    private val _ipAddress = MutableLiveData<IPList>()
    val ipAddress : LiveData<IPList> = _ipAddress

    override val repository: IpRepositoryImpl
        get() = this

    override suspend fun getIpList(action: (IPList) -> Unit) {
        onShowDialog(true)
        val response = safeApiCall { apiIpList.getAPIIp() }
        response.onSuccess {
            _ipAddress.postValue(it)
            action.invoke(it)
        }
    }

    override suspend fun writeGoogleSheetVietNam(account: Account): Flow<Result<Any>> {
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
        onShowDialog()

        return flow {
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