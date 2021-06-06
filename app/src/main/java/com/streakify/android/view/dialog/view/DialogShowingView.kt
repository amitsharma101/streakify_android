package com.streakify.android.view.dialog.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.streakify.android.view.dialog.IDialogViewModel
import com.streakify.android.view.dialog.common.DialogUiConfig

import com.streakify.android.BR


class DialogShowingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : BaseShowingView(context, attrs, defStyleAttr) {

    var bindingData: Pair<DialogUiConfig?, IDialogViewModel?>? = null
        set(value) {
            field = value
            value?.let { (config, viewModel) ->
                binding.setVariable(BR.uiConfig, config)
                binding.setVariable(BR.viewModel, viewModel)
            }
        }
}

@BindingAdapter(value = ["dialogConfig", "dialogViewModel"], requireAll = false)
fun DialogShowingView.bindTextAndActions(
    dialogConfig: DialogUiConfig? = null,
    dialogViewModel: IDialogViewModel? = null
) {

    cancelable = dialogConfig?.cancelable ?: false

    dialog.setCancelable(cancelable)

    bindingData = Pair(dialogConfig, dialogViewModel)
}

fun DialogShowingView.show(
    show: Boolean? = null,
) {
    if (show != null)
        visibility = if (show) View.VISIBLE else View.GONE
}