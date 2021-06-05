package com.streakify.android.view.binding

import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.streakify.android.R
import com.google.android.material.snackbar.Snackbar


class CustomBindings {

    companion object {

        @BindingAdapter("error", "isValid", "isErrorShown")
        @JvmStatic
        fun setError(
            editText: AppCompatEditText,
            errorMessage: String?,
            isValid: Boolean,
            isErrorShown: Boolean
        ) {
            if (errorMessage != "") {
                if (isErrorShown && !isValid) {
                    editText.error = errorMessage
                }
            }
        }


        @BindingAdapter("imageurl")
        @JvmStatic
        fun bindImageView(view: AppCompatImageView, url: String) {
            Glide.with(view.context)
                .load(url)
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop()
                        .transform(CenterCrop(), RoundedCorners(1000))
                )
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("onClick")
        fun View.onClick(listener: (() -> Unit)?) {
            setOnClickListener {
                listener?.invoke()
            }
        }


    }
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(isVisible: Boolean?) {
    if (isVisible != null) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
@BindingAdapter("visibleOrInvisible")
fun View.visibleOrInvisible(isVisible: Boolean?) {
    if (isVisible != null) {
        visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}


@BindingAdapter("scrollable")
fun TextView.scrollable(scrollable: Boolean?) {
    if (scrollable != null && scrollable) {
        movementMethod = ScrollingMovementMethod()
    }
}


@BindingAdapter("onLongClick")
fun View.setOnLongClickListener(listener: (() -> Unit)?) {
    setOnLongClickListener {
        listener?.invoke()
        true
    }
}

@BindingAdapter("snackbarMessage")
fun View.snackBarMessage(message: String?) {
    if (!message.isNullOrEmpty()) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }
}

