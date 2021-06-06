package com.streakify.android.view.dialog.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.databinding.BindingAdapter
import com.streakify.android.view.dialog.common.LoadingUiConfig
import com.streakify.android.BR


@SuppressLint("Recycle")
class LoadingShowingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : BaseShowingView(context, attrs, defStyleAttr) {

    var bindingData: LoadingUiConfig? = null
        set(value) {
            field = value
            value?.let {
                binding.setVariable(BR.uiConfig, it)
            }
        }
}

@BindingAdapter(value = ["loadingDialogConfig"], requireAll = false)
fun LoadingShowingView.bindTextAndActions(
    dialogConfig: LoadingUiConfig? = null
) {
    bindingData = dialogConfig
}