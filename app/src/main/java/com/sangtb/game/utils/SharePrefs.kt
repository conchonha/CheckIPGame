package com.sangtb.game.utils;

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.sangtb.game.data.Account
import javax.inject.Inject

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/12/2022
*/

class SharePrefs @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {
    private val gson = Gson()

    fun saveAccount(account: Account) {
        editor.putString(Const.ACCOUNT, gson.toJson(account)).commit()
    }

    fun checkAccount() = sharedPreferences.getString(Const.ACCOUNT, DEFAULT_VALUE) != DEFAULT_VALUE

    fun getAccount(): Account {
        return gson.fromJson(sharedPreferences.getString(Const.ACCOUNT, DEFAULT_VALUE), Account::class.java)
    }

    companion object{
        private const val DEFAULT_VALUE = ""
    }
}
