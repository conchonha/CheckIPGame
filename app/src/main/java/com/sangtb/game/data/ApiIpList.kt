package com.sangtb.game.data

import retrofit2.http.GET

interface ApiIpList {

    @GET("api")
    suspend fun getAPIIp(): IPList
}