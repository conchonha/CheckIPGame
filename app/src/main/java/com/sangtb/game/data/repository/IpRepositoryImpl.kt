package com.sangtb.game.data.repository

import com.sangtb.androidlibrary.utils.SingleLiveEvent
import com.sangtb.game.data.Account
import com.sangtb.game.data.ApiIpList
import com.sangtb.game.data.response.IPList
import com.sangtb.game.di.ApiIPAddress
import com.sangtb.game.di.ApiIPVietNam
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class IpRepositoryImpl @Inject constructor(
    @ApiIPAddress private val apiIpList: ApiIpList,
    @ApiIPVietNam private val apiIPVietNam: ApiIpList
) : IpRepository() {
    private val _showToastError = SingleLiveEvent<Throwable>()
    val showToastError: SingleLiveEvent<Throwable> = _showToastError

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
        _showToastError.postValue(throwable)
    }
}