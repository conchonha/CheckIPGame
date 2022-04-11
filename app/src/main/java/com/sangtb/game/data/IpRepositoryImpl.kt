package com.sangtb.game.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IpRepositoryImpl @Inject constructor(private val apiIpList: ApiIpList) : IpRepository() {
    override fun getIpList(): Flow<Result<IPList>> {
        return flow {
            val response = safeApiCall{
                apiIpList.getAPIIp()
            }
            Log.d("IpRepositoryImpl", "getIpList: $response")
            emit(response)
        }
    }
}