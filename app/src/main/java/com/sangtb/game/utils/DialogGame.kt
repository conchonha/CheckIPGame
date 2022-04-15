package com.sangtb.game.utils;

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import com.sangtb.androidlibrary.utils.DialogLibrary
import com.sangtb.game.R
import com.sangtb.game.databinding.DialogLayoutBinding
import com.sangtb.game.ui.introduce.IntroduceViewModel

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/15/2022
*/

class DialogGame : DialogLibrary<DialogLayoutBinding,Any>(){
    override val layout: Int
        get() = R.layout.dialog_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.action = this
    }

    override fun onAccept() {
        dismiss()
    }

    fun setCanceler() : DialogGame{
        isCancelable = false
        return this
    }
}
