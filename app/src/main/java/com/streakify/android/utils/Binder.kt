package com.streakify.android.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.streakify.android.R

object Binder {
    @JvmStatic
    @BindingAdapter(value = ["app:shortText"], requireAll = true)
    fun loadImage(view: ImageView, shortText: String) {
        var drawable: Drawable? = null
        if (drawable == null && !TextUtils.isEmpty(shortText)) {
            drawable = BitmapDrawable(view.resources, getBitmap(view.context, shortText, false))
            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            view.setImageDrawable(drawable)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:loadStreakImg"], requireAll = true)
    fun loadStreakImage(view: CircularStreakView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply( RequestOptions().placeholder(R.drawable.ic_alarm_clock)
                .error(R.drawable.ic_alarm_clock))
            .into(view)
    }

    private fun getBitmap(context: Context, name: String, isSupplier: Boolean): Bitmap {
        val textDrawer = ImageHelper.getTextDrawer(name, 2, context, isSupplier)
        val size = context.resources.getDimension(R.dimen.size_40).toInt()
        return ImageHelper.createCircleImageFromText(context, size, size, textDrawer)
    }

    @JvmStatic
    @BindingAdapter("setFocusable")
    fun setFieldFocusable(view: View, size:Int){
        view.isFocusable = size==0
    }

    @JvmStatic
    @BindingAdapter("setVisibility")
    fun setVisibility(view: View, size:Int){
        if (size==0){
            view.visibility = View.VISIBLE
        }else{
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("setsCheckable")
    fun setsRbCheckable(view: RadioButton, size:Int){
        view.isClickable = size==0
        view.isEnabled = size==0
    }

    @JvmStatic
    @BindingAdapter("maxDigits")
    fun maxDigits(editText: TextInputEditText,maxDigits:Int){
        editText.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter( maxDigits))
    }

}