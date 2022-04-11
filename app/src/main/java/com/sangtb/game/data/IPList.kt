package com.sangtb.game.data

data class IPList(
    val ip: String = "",
    val register: String = "",
    val countrycode: String = "",
    val countryname: String = "",
    val detail: String = "",
    val spam: Boolean = false,
    val tor: Boolean = false
)