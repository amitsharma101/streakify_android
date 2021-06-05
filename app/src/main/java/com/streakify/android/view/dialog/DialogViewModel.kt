package com.streakify.android.view.dialog

class DialogViewModel(
    private val positiveClick: (() -> Unit)? = null,
    private val negativeClick: (() -> Unit)? = null
) : IDialogViewModel {

    override fun onPositiveButtonClick() {
        positiveClick?.invoke()
    }

    override fun onNegativeButtonClick() {
        negativeClick?.invoke()
    }
}