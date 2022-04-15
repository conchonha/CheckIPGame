package com.sangtb.game.utils

import android.util.Log
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Singleton
class Helpers{
    companion object {
        private val TAG by lazy { this.javaClass.name }
    }

    fun checkInterNetVN(countryCode: String?): Boolean {
        val tz = Calendar.getInstance().timeZone
        val boolean = countryCode == Const.COUNTRY_CODE_VIETNAME && tz.id == Const.TIME_ZONE_VIETNAM

        Log.d(TAG, "country: $countryCode --- TimeZone: ${tz.id}")
        return boolean
    }
}