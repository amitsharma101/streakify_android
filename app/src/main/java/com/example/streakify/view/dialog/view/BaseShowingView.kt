package com.example.streakify.view.dialog.view
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.streakify.R

//private const val EMPTY_RESOURCE = -1
//
//@SuppressLint("Recycle")
//abstract class BaseShowingView(
//    context: Context,
//    attrs: AttributeSet? = null,
//    @AttrRes defStyleAttr: Int = 0
//) : View(context, attrs) {
//
//    lateinit var dialog: Dialog
//
//    lateinit var binding: ViewDataBinding
//
//    var cancelable = false
//
//    private var dialogVisibility: Int = GONE
//        set(value) {
//            field = value
//            if (value == VISIBLE) {
//                dialog.show()
//            } else {
//                dialog.dismiss()
//            }
//        }
//
//    init {
//        context.obtainStyledAttributes(attrs, R.styleable.BaseShowingView, defStyleAttr, 0).use {
//            @StyleRes
//            val dialogStyle =
//                it.getResourceId(R.styleable.BaseShowingView_dialogStyle, EMPTY_RESOURCE)
//            require(dialogStyle != EMPTY_RESOURCE) {
//                "Dialog style must be defined"
//            }
//
//            @LayoutRes
//            val dialogLayout =
//                it.getResourceId(R.styleable.BaseShowingView_viewLayout, EMPTY_RESOURCE)
//            require(dialogLayout != EMPTY_RESOURCE) {
//                "Dialog layout must be defined"
//            }
//
//            val cancelable =
//                it.getBoolean(R.styleable.BaseShowingView_cancelable, cancelable)
//
//            createDialog(context, dialogLayout, dialogStyle, cancelable)
//        }
//    }
//
//    private fun createDialog(
//        context: Context,
//        @LayoutRes dialogLayout: Int,
//        @StyleRes dialogStyle: Int,
//        cancelable: Boolean
//    ) {
//        val frameLayout = FrameLayout(context)
//
//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            dialogLayout,
//            frameLayout,
//            true
//        )
//
//        dialog = Dialog(context, dialogStyle).apply {
//            setContentView(frameLayout)
//            setCancelable(cancelable)
//            window?.setBackgroundDrawableResource(android.R.color.transparent)
//        }
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        setMeasuredDimension(0, 0)
//    }
//
//    override fun setVisibility(visibility: Int) {
//        dialogVisibility = visibility
//    }
//
//    override fun getVisibility() = dialogVisibility
//
//    /**
//     * Sometimes while showing the dialog we need to replace its holder fragment or activity. In this case we
//     * need to dismiss dialog.
//     */
//    override fun onDetachedFromWindow() {
//        dialog.dismiss()
//        super.onDetachedFromWindow()
//    }
//}