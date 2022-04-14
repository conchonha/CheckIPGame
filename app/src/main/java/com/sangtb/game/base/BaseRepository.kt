package com.sangtb.game.base

import com.sangtb.game.data.repository.IpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseRepository {
    abstract val repository: IpRepository?

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(apiCall.invoke())
            } catch (throwable: Throwable) {
                repository?.onFail(throwable)
                Result.failure(throwable)
            }
        }
    }
}
