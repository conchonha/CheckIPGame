package com.sangtb.game.data.repository

import com.sangtb.game.base.BaseRepository
import com.sangtb.game.data.response.CodeIntroduce
import com.sangtb.game.data.response.Diendanxoc
import com.sangtb.game.data.response.LinkKu
import kotlinx.coroutines.flow.Flow

abstract class DataFireBaseRepository : BaseRepository() {

    companion object {
        val TAG by lazy { this.javaClass.name }
    }

    abstract fun getCodeIntroduce(response: (List<CodeIntroduce>) -> Unit)

    abstract fun getLinkku(response: (List<LinkKu>) -> Unit)

    abstract fun getLinkDiendanxoc(response: (List<Diendanxoc>) -> Unit)
}