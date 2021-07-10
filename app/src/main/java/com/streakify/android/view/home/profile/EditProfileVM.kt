package com.streakify.android.view.home.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.module.NetworkModule
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.Util
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.profile.data.MyProfile
import com.streakify.android.view.home.profile.data.UpdateProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class EditProfileVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val commonRepository: CommonRepository,
    val localPreferences: LocalPreferences,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(networkLiveData) {

    var selectedImageThumbnail = MutableLiveData<Bitmap?>(null)
    var selectedImageFileCompressed: File? = null
    var selectedImageUri: Uri? = null
    var selectedImageUrl = MutableLiveData<String?>(null)

    var profile = ObservableField<MyProfile>().apply {
        addPropertyChangedCallback {
            if(get()!=null){
                val pro = get()
                name.set(pro?.name)
                email.set(pro?.email)
                phone.set(pro?.mobileNumber)

                if(!pro?.profilePic.isNullOrBlank()){
                    selectedImageUrl.value = pro?.profilePic
                }
            }
        }
    }


    val name = ObservableField("")
    val email = ObservableField("")
    val phone = ObservableField("")

    init {

    }

    fun onAttach(){

    }

    suspend fun updateProfileDetails(profilePic:String?){
        val req = UpdateProfileRequest(
            name = name.get(),
            email = email.get(),
            profilePic = profilePic
        )
        val apiResponse = commonRepository.updateProfile(req)
        when(apiResponse){
            is NetworkResponse.Success -> {
                event.value = ProfileEvents.ProfileUpdatedEvent
            }
            is NetworkResponse.ApiError -> {
                eventListener.dismissLoading()
                eventListener.showMessageDialog(apiResponse.error?.detail,
                    "Oops",
                    positiveClick = {
                        eventListener.dismissMessageDialog()
                    })
            }
            else -> {
                eventListener.dismissLoading()
            }
        }
    }



    fun updateProfile(){
        viewModelScope.launch {
                eventListener.showLoading()

            if(selectedImageFileCompressed == null){
                updateProfileDetails(null)
            }
            else{
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        if (selectedImageFileCompressed != null) {
            viewModelScope.launch {
                val image: RequestBody = selectedImageFileCompressed!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                val apiResponse = commonRepository.pushImagesData(image)

                when (apiResponse) {
                    is NetworkResponse.Success -> {
                        val imgUrl = apiResponse.body?.body?.image

                        imgUrl?.let {
                           updateProfileDetails(imgUrl)
                        }
                    }
                    else -> {
                        eventListener.dismissLoading()
                        eventListener.showMessageDialog(message = apiResponse.toString())
                    }
                }
            }
        }
    }

    fun updateSelectedImage(context: Context, uri: Uri?, containerWidth: Int) {
        viewModelScope.launch {
            selectedImageUri = uri
            if (uri != null) {
                withContext(Dispatchers.IO) {
                    eventListener.showLoading()
                    val thumbnail = Util.getThumbnailFromUri(context, uri, containerWidth)
                    val compressedFile = Util.getCompressedImageFileFromUri(context, uri)

                    eventListener.dismissLoading()
                    withContext(Dispatchers.Main) {
                        selectedImageThumbnail.value = thumbnail
                        selectedImageFileCompressed = compressedFile
                    }
                }
            } else {
                selectedImageThumbnail.value = null
            }
        }
    }

}