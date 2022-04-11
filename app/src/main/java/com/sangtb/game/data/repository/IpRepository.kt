package com.sangtb.game.data.repository

import com.sangtb.game.base.BaseRepository
import com.sangtb.game.data.IPList
import kotlinx.coroutines.flow.Flow

abstract class IpRepository : BaseRepository(){
    abstract suspend fun getIpList()
}