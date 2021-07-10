package com.streakify.android.utils

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import com.streakify.android.R
import kotlin.math.abs

object ImageHelper {
    fun getTextDrawer(name: String, characters: Int, context: Context, isSupplier: Boolean): TextDrawer {
        val letter = getInitialise(name, characters)
        return TextDrawer().setText(letter)
            .setBackgroundColor(getRandomColor(letter, context, isSupplier))
            .setTextColor(Color.WHITE)
    }

    private fun getInitialise(text: String, characters: Int): String {
        var name = text
        var initials = ""

        if (TextUtils.isEmpty(name)) {
            return initials
        }

        name = name.trim { it <= ' ' }
        val split = name.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (split.isEmpty()) {
            return initials
        }

        if (TextUtils.isEmpty(split[0])) {
            return initials
        }

        initials = initials + split[0][0].toString().toUpperCase() + if (split.size > 1 && characters > 1) split[split.size - 1][0].toString().toUpperCase() else ""

        return initials
    }

    private fun getRandomColor(letter: String, context: Context, isSupplier: Boolean): Int {
        var tempRnd = 0
        letter.forEach {
            tempRnd += it.toByte().toInt()
        }
        val customerMaterialColors: IntArray = context.resources.getIntArray(R.array.customerMaterialColors)
        return customerMaterialColors[abs(tempRnd % 4)]
    }

    fun createCircleImageFromText(context: Context, width: Int, height: Int, textDrawer: TextDrawer): Bitmap {

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paintCircle = Paint()

        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)
        val density = context.resources.displayMetrics.density

        paintCircle.color = textDrawer.getBackgroundColor()
        paintCircle.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)

        paintCircle.style = Paint.Style.FILL

        canvas.drawRoundRect(rectF, (width / 2).toFloat(), (height / 2).toFloat(), paintCircle)

        val textSize = density * 20
        val paintText = Paint()
        paintText.color = textDrawer.getTextColor()
        paintText.textSize = textSize
        paintText.textAlign = Paint.Align.CENTER
        paintText.isAntiAlias = true

        var textWidth = paintText.measureText(textDrawer.getText())

        while (textWidth > width) {
            paintText.textSize = paintText.textSize - 5
            textWidth = paintText.measureText(textDrawer.getText())
        }

        val xPos = canvas.width / 2
        val yPos = (canvas.height / 2 - (paintText.descent() + paintText.ascent()) / 2).toInt()

        canvas.drawText(textDrawer.getText(), xPos.toFloat(), yPos.toFloat(), paintText)

        return output
    }
}