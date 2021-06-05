package com.streakify.android.view.dialog.view


//class DialogShowingView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    @AttrRes defStyleAttr: Int = 0
//) : BaseShowingView(context, attrs, defStyleAttr) {
//
//    var bindingData: Pair<DialogUiConfig?, IDialogViewModel?>? = null
//        set(value) {
//            field = value
//            value?.let { (config, viewModel) ->
//                binding.setVariable(BR.uiConfig, config)
//                binding.setVariable(BR.viewModel, viewModel)
//            }
//        }
//}
//
//@BindingAdapter(value = ["dialogConfig", "dialogViewModel"], requireAll = false)
//fun DialogShowingView.bindTextAndActions(
//    dialogConfig: DialogUiConfig? = null,
//    dialogViewModel: IDialogViewModel? = null
//) {
//
//    cancelable = dialogConfig?.cancelable ?: false
//
//    dialog.setCancelable(cancelable)
//
//    bindingData = Pair(dialogConfig, dialogViewModel)
//}
//
//fun DialogShowingView.show(
//    show: Boolean? = null,
//) {
//    if (show != null)
//        visibility = if (show) View.VISIBLE else View.GONE
//}