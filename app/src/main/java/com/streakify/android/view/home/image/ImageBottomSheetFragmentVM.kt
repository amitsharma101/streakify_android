package com.streakify.android.view.home.image

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.streakify.android.R
import com.streakify.android.di.provider.ResourceProvider
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import javax.inject.Inject

class ImageBottomSheetFragmentVM @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    var selectedFileData = MutableLiveData<ImageBottomSheetFragment.SharedFileData?>()

    fun getFileForImage(): File? {
        try {
            val folderPath =
                Environment.getExternalStoragePublicDirectory(resourceProvider.getString(R.string.app_name) + "/images").absolutePath
            val filePath = folderPath + "/" + UUID.randomUUID().toString() + ".jpg"
            val folder = File(folderPath)
            if (!folder.exists())
                folder.mkdirs()

            return File(filePath)
        } catch (e: FileNotFoundException) {
            return null
        }

    }

}
