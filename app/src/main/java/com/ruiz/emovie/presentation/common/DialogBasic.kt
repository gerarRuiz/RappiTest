package com.ruiz.emovie.presentation.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ruiz.emovie.R
import com.ruiz.emovie.databinding.AlertDialogBasicBinding
import com.ruiz.emovie.util.constants.ConstantsDialogs.DIALOG_DESCRIPTION
import com.ruiz.emovie.util.constants.ConstantsDialogs.DIALOG_ICON_NAME
import com.ruiz.emovie.util.constants.ConstantsDialogs.DIALOG_TEXT_BUTTON
import com.ruiz.emovie.util.constants.ConstantsDialogs.DIALOG_TITLE
import com.ruiz.emovie.util.enums.DialogAnim

class DialogBasic : DialogFragment() {

    private var binding: AlertDialogBasicBinding? = null
    private var onAction: () -> Unit = {}

    companion object {

        fun newInstance(
            title: String,
            description: String,
            textButton: String,
            icon: DialogAnim,
        ): DialogBasic {
            return DialogBasic().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_TITLE, title)
                    putString(DIALOG_DESCRIPTION, description)
                    putString(DIALOG_TEXT_BUTTON, textButton)
                    putString(DIALOG_ICON_NAME, icon.name)
                }
            }
        }

    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            window.setLayout(width, height)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlertDialogBasicBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            it.getString(DIALOG_TITLE)?.let { title ->
                binding?.dialogTitle?.text = title
            }
            it.getString(DIALOG_DESCRIPTION)?.let { description ->
                binding?.dialogMessage?.text = description
            }
            it.getString(DIALOG_TEXT_BUTTON)?.let { textButton ->
                binding?.dialogButton?.text = textButton
            }
            it.getString(DIALOG_ICON_NAME)?.let { iconName ->
                val anim = DialogAnim.valueOf(iconName)
                binding?.dialogAnim?.let { dialogAnimation ->
                    when (anim) {
                        DialogAnim.INFORMATIVE -> dialogAnimation.setAnimation(R.raw.anim_info)
                        DialogAnim.ERROR -> dialogAnimation.setAnimation(R.raw.anim_error)
                    }
                }
            }

        }

        binding?.dialogButton?.setOnClickListener {
            dismiss()
            onAction()
        }

    }

    fun setOnAction(onAction: () -> Unit) {
        this.onAction = onAction
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}