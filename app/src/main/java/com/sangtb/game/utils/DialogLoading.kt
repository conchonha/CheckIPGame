package com.sangtb.game.utils;

import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.sangtb.game.R

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/15/2022
*/

class DialogLoading private constructor() : DialogFragment(R.layout.dialog_loading) {

    override fun onResume() {
        dialog?.window?.apply {
            setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setDimAmount(Const.DI_AMOUNT)
        }
        super.onResume()
    }

    companion object {
        @Volatile
        private var instance: DialogLoading? = null

        fun getInstance() = synchronized(this) {
            instance ?: DialogLoading().also { instance = it }
        }
    }
}
