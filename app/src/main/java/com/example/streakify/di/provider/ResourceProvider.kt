package com.example.streakify.di.provider

import android.content.Context
import android.content.res.Resources
import androidx.annotation.*
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(private val context: Context) {

    /** @return Resources */
    private fun getResources(): Resources = context.resources

    /** @return String Value from StringResource */
    fun getString(@StringRes stringId: Int): String = getResources().getString(stringId)

    /** @return String Value from StringResource */
    fun getString(@StringRes stringID: Int, vararg formatArgs: Any?): String =
        getResources().getString(stringID, *formatArgs)

    /** @return Drawable Value from Drawable Resource */
    fun getDrawable(@DrawableRes drawableId: Int) = ContextCompat.getDrawable(context, drawableId)

    /** @return Color Value from Color Resource */
    fun getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(context, colorId)

    /** @eturn Integer Value from Integer Resource */
    fun getInteger(@IntegerRes integerId: Int) = getResources().getInteger(integerId)
    /** @eturn Integer Value from Integer Resource */
    fun getDimens(@DimenRes id: Int) = getResources().getDimension(id)

}