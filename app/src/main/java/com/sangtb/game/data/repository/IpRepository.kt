package com.sangtb.game.data.repository

import com.sangtb.game.base.BaseRepository
import com.sangtb.game.data.Account
import com.sangtb.game.data.response.IPList
import kotlinx.coroutines.flow.Flow

abstract class IpRepository : BaseRepository(){
    abstract suspend fun getIpList(action: (IPList)->Unit)

    abstract fun onFail(throwable: Throwable)

    abstract fun onShowDialog(boolean: Boolean? = false)

    abstract suspend fun writeGoogleSheetVietNam(account: Account) : Flow<Result<Any>>
}