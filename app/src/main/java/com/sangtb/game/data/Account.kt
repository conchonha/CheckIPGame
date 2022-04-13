package com.sangtb.game.data;

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/12/2022
*/
data class Account(
    var name: String,
    var phone: String,
    var accountKU: String,
    var password: String,
    var ip : String? = null,
    var action: String = "addIpAddress"
)
