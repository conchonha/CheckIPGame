package com.sangtb.game.data.repository

import com.sangtb.game.data.ApiIpList
import com.sangtb.game.data.IPList
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class IpRepositoryImpl @Inject constructor(private val apiIpList: ApiIpList) : IpRepository() {
    private val _ipAddress = MutableSharedFlow<Result<IPList>>()
    val ipAddress  = _ipAddress.asSharedFlow()

    override suspend fun getIpList() {
        _ipAddress.emit(safeApiCall { apiIpList.getAPIIp() })
    }
}