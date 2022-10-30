package com.ruiz.rappitest.util.extensions

import androidx.fragment.app.Fragment
import com.ruiz.rappitest.presentation.common.DialogBasic
import com.ruiz.rappitest.util.constants.ConstantsDialogs.DIALOG_BASIC
import com.ruiz.rappitest.util.enums.DialogAnim

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