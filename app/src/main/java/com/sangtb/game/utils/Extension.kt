package com.sangtb.game.utils;

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.sangtb.game.MainActivity
import com.sangtb.game.base.BaseRepository

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/9/2022
*/

fun Fragment.onNavigate(actionId: Int, bundle: Bundle? = null) {
    (activity as? MainActivity)?.onNavigate(actionId, bundle)
}

fun Fragment.onBackScreen() {
    (activity as? MainActivity)?.onBackStack()
}

fun MutableLiveData<String>.isEmpty1() =
    value?.isEmpty() == true

fun Activity.showToast(error : String){
    Toast.makeText(this,error,Toast.LENGTH_LONG).show()
}

