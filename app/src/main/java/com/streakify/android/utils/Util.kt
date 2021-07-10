package com.streakify.android.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.ParcelFileDescriptor
import okhttp3.internal.trimSubstring
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class Util {
    companion object{

        val MAX_IMAGE_UPLOAD_SIZE = 5
        val THRESHOLD_IMAGE_DIMENSION = 1920f

        fun getDifferenceDays(d1: Date, d2: Date): Long {
            val diff: Long = d2.getTime() - d1.getTime()
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        }

        fun getHash(id: String): Long {
            val upper = id.hashCode().toLong() shl 32
            val lower = reverse(id).hashCode().toLong() - Int.MIN_VALUE.toLong()
            return upper + lower
        }

        fun reverse(str: String): String {
            val sb = StringBuilder()
            for (i in str.length - 1 downTo 0) {
                sb.append(str[i])
            }
            return sb.toString()
        }

        fun purifyMobileNumber(number: String): String {
            var number = number
            if (isBlank(number)) return number
            val m = Pattern.compile("[^0-9+]").matcher(number)
            number = m.replaceAll("")

            // removing 0 (don't allow it at start of number for INDIA ONLY)
            if (number.length > 10) {
                if (number.substring(0, 1).equals("0", ignoreCase = true)) {
                    number = number.substring(1)
                }
            }
            if (number.length > 10) {
                // assume that it to contain either 0 or country code(91 or +91)
                if (!number.startsWith("+")) {
                    val fakeNumber = "+$number" //add + to make it +91xxxx (from 91xxxx)
                    // so that removedCountryCodeFromMobileNo works
                    val newNumber = removedCountryCodeFromMobileNo(fakeNumber)
                    return if (newNumber != fakeNumber) {
                        // country code removed successfully
                        newNumber
                    } else {
                        // nothing changed, so just return original number
                        number
                    }
                }
            }
            return removedCountryCodeFromMobileNo(number)
        }

        private fun removedCountryCodeFromMobileNo(number: String): String {
            for (country in Countries().getCountryList()) {
                val countryCode: String = country.code
                if (number.startsWith(countryCode)) {
                    return number.substring(countryCode.length)
                }
            }
            return number
        }

        fun isBlank(str: String?): Boolean {
            return str == null || str.trim { it <= ' ' } == ""
        }

        fun trim(text:String):String{
            var result = ""
            val tokens = text.split(" ")
            tokens.forEach {
                result += it
            }
            return result
        }

        fun getThumbnailFromUri(context: Context, uri: Uri?, requiredThumbSize: Int): Bitmap? {
            try {
                val bitmap: Bitmap? = getBitmapFromUri(context, uri)
                bitmap?.let {
                    var scale = 1f
                    if (bitmap.width > bitmap.height)
                        scale = requiredThumbSize.toFloat() / bitmap.width
                    else
                        scale = requiredThumbSize.toFloat() / bitmap.height
                    return ThumbnailUtils.extractThumbnail(
                        bitmap,
                        (bitmap.width * scale).toInt(),
                        (bitmap.height * scale).toInt()
                    )
                }
                return null
            } catch (e: Exception) {
                return null
            }
        }

        private fun getBitmapFromUri(context: Context, uri: Uri?): Bitmap? {
            try {
                uri?.let {
                    val parcelFileDescriptor: ParcelFileDescriptor? =
                        context.contentResolver.openFileDescriptor(uri, "r")
                    val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                    val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                    parcelFileDescriptor?.close()
                    return image
                }
            } catch (e: Exception) {

            }
            return null
        }


        fun getCompressedImageFileFromUri(context: Context, uri: Uri?): File? {
            try {
                val tempFilePath = context.cacheDir.absolutePath + "/temp.jpg"

                var bitmap: Bitmap? = getBitmapFromUri(context, uri)
                bitmap?.let {
                    writeBitmapToFile(tempFilePath, it, 100)
                    if (getFileSizeInMb(File(tempFilePath)) < MAX_IMAGE_UPLOAD_SIZE)
                        return File(tempFilePath)
                    else {
                        // Compression Logic required
                        if (it.width > THRESHOLD_IMAGE_DIMENSION || it.height > THRESHOLD_IMAGE_DIMENSION) {
                            //Resizing the Image to Meet Threshold
                            var scale = 1f
                            if (it.width > it.height)
                                scale = THRESHOLD_IMAGE_DIMENSION / it.width
                            else
                                scale = THRESHOLD_IMAGE_DIMENSION / it.height

                            bitmap = Bitmap.createScaledBitmap(
                                it, (it.width * scale).toInt(),
                                (it.height * scale).toInt(), true
                            );
                            writeBitmapToFile(tempFilePath, bitmap!!, 100)

                        }

                        // Further Compressing if Image size is greater then the Max File Size of 5 MB
                        var noOfAttempts = 1
                        while (getFileSizeInMb(File(tempFilePath)) > MAX_IMAGE_UPLOAD_SIZE && noOfAttempts <= 9) {
                            writeBitmapToFile(tempFilePath, bitmap!!, 100 - 10 * noOfAttempts)
                            noOfAttempts++
                        }

                        return File(tempFilePath)
                    }
                }
                return null
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun getFileSizeInMb(file: File): Int {
            return file.length().div(1024 * 1024).toInt()
        }

        fun writeBitmapToFile(filePath: String, bitmap: Bitmap, compression: Int): Boolean {
            try {
                val fOut = FileOutputStream(File(filePath))
                bitmap.compress(Bitmap.CompressFormat.JPEG, compression, fOut)
                fOut.flush()
                fOut.close()
                return true
            } catch (ex: Exception) {
                return false
            }

        }

        fun getFileSizeFromUriInMb(context: Context, uri: Uri?): Int? {
            try {
                uri?.let {
                    val parcelFileDescriptor: ParcelFileDescriptor? =
                        context.contentResolver.openFileDescriptor(uri, "r")
                    val size = parcelFileDescriptor?.statSize?.div(1024 * 1024)?.toInt()
                    parcelFileDescriptor?.close()
                    return size
                }
            } catch (e: Exception) {

            }
            return null
        }




    }
}