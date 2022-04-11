package com.sangtb.game.data

import com.sangtb.game.base.BaseRepository
import kotlinx.coroutines.flow.Flow

abstract class IpRepository : BaseRepository(){
    abstract fun getIpList() : Flow<Result<IPList>>
}