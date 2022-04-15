package com.sangtb.game.base;

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.sangtb.game.R
import com.sangtb.game.utils.Const
import com.sangtb.game.utils.DialogLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/15/2022
*/

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG by lazy { this.javaClass.name }
    private var mProgressDialog: ProgressDialog? = null

    private val dialog = DialogLoading.getInstance()

    fun showProgressDialog() {
        if(dialog.isVisible){
            dialog.dismiss()
        }
        dialog.show(supportFragmentManager,Const.TAG)
    }

    fun hideDialog() {
        if(dialog.isVisible){
            dialog.dismiss()
        }
    }
}
