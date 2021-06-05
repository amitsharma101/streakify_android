package com.streakify.android.view.dialog.view

//import com.example.streakify.view.dialog.common.LoadingUiConfig

//
//@SuppressLint("Recycle")
//class LoadingShowingView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    @AttrRes defStyleAttr: Int = 0
//) : BaseShowingView(context, attrs, defStyleAttr) {
//
//    var bindingData: LoadingUiConfig? = null
//        set(value) {
//            field = value
//            value?.let {
//                binding.setVariable(BR.uiConfig, it)
//            }
//        }
//}
//
//@BindingAdapter(value = ["loadingDialogConfig"], requireAll = false)
//fun LoadingShowingView.bindTextAndActions(
//    dialogConfig: LoadingUiConfig? = null
//) {
//    bindingData = dialogConfig
//}