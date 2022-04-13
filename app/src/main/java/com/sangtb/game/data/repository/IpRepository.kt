package com.sangtb.game.data.repository

import com.sangtb.game.base.BaseRepository
import com.sangtb.game.data.Account
import kotlinx.coroutines.flow.Flow

abstract class IpRepository : BaseRepository(){
    abstract suspend fun getIpList()

    abstract fun onFail(throwable: Throwable)

    abstract suspend fun writeGoogleSheetVietNam(account: Account) : Flow<Result<Any>>
}