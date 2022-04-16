package com.sangtb.game.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.util.Calendar
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

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
        return result
    }
}