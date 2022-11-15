package com.ruiz.emovie.util.extensions

import androidx.fragment.app.Fragment
import com.ruiz.emovie.presentation.common.DialogBasic
import com.ruiz.emovie.util.constants.ConstantsDialogs.DIALOG_BASIC
import com.ruiz.emovie.util.enums.DialogAnim

fun Fragment.showBasicDialog(
    title: String,
    message: String,
    textButton: String,
    anim: DialogAnim,
    onAction: () -> Unit
) {

    DialogBasic.newInstance(
        title,
        message,
        textButton,
        anim
    ).apply {
        this.setOnAction(onAction)
    }.show(childFragmentManager, DIALOG_BASIC)

}