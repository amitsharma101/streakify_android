package com.streakify.android.view.home.streaks.editstreak

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseViewModel
import com.streakify.android.commonrepo.CommonRepository
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.networkcall.NetworkResponse
import com.streakify.android.utils.AUTH_TOKEN
import com.streakify.android.utils.LocalPreferences
import com.streakify.android.utils.Util
import com.streakify.android.utils.extensions.Extensions.Companion.addPropertyChangedCallback
import com.streakify.android.utils.network.NetworkLiveData
import com.streakify.android.view.dialog.common.EventListener
import com.streakify.android.view.home.friends.firendslist.data.ActiveFriendsItem
import com.streakify.android.view.home.onboarding.repo.AuthRepository
import com.streakify.android.view.home.streaks.StreaksEvent
import com.streakify.android.view.home.streaks.editstreak.data.AddFriendBSItemVM
import com.streakify.android.view.home.streaks.editstreak.data.CreateStreakRequest
import com.streakify.android.view.home.streaks.editstreak.data.FriendsRecordItem
import com.streakify.android.view.home.streaks.editstreak.data.UpdateStreakRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

class EditStreakVM @Inject constructor(
    networkLiveData: NetworkLiveData,
    val eventListener: EventListener,
    val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    val localPreferences: LocalPreferences,
    val commonRepository: CommonRepository
) : BaseViewModel(networkLiveData) {

    var selectedImageThumbnail = MutableLiveData<Bitmap?>(null)
    var selectedImageFileCompressed: File? = null
    var selectedImageUri: Uri? = null
    var selectedImageUrl = MutableLiveData<String?>(null)

    val sdf = SimpleDateFormat(AppConstants.APP_DATE_FORMAT)



    val streakName = ObservableField("")
    val streakType = ObservableInt(AppConstants.STREAK_TYPE_INDEFINITE).apply {
        addPropertyChangedCallback {
            if (get() == AppConstants.STREAK_TYPE_INDEFINITE) {
                setGoalVisibility.set(View.GONE)
            } else {
                setGoalVisibility.set(View.VISIBLE)
            }
        }
    }
    val streakMaxDays = ObservableField("")

    val setGoalVisibility = ObservableInt(View.GONE)

    var streakStartDate = sdf.format(Date())

    val deleteIconVisibility = ObservableInt(View.GONE)

    init {

    }

    var friends: List<ActiveFriendsItem?>? = null
    val frindsInStreak = mutableListOf<ActiveFriendsItem>()


    var streakId: Int? = null

    fun onAttach(streakId: Int?) {

        sdf.timeZone = TimeZone.getTimeZone("IST")
        streakStartDate = sdf.format(Date())

        this.streakId = streakId
        viewModelScope.launch {
            eventListener.showLoading()
            val apiResponse = commonRepository.getFriends()
            when (apiResponse) {
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    friends = apiResponse.body?.body?.activeFriends


                    if (streakId == null || streakId == -1) {
                        deleteIconVisibility.set(View.GONE)
                    } else {
                        deleteIconVisibility.set(View.VISIBLE)
                        eventListener.showLoading()
                        val currentStreakDetail = commonRepository.streakDetail(streakId.toString())
                        when (currentStreakDetail) {
                            is NetworkResponse.Success -> {
                                eventListener.dismissLoading()
                                val streak = currentStreakDetail.body?.body

                                if (!streak?.streakImage.isNullOrBlank()) {
                                    selectedImageUrl.value = streak?.streakImage
                                }

                                streakName.set(streak?.name)
                                streakType.set(streak?.type!!)
                                when (streak.type) {
                                    AppConstants.STREAK_TYPE_DEFINITE -> {
                                        event.value = StreaksEvent.DeifiniteClickedEvent
                                    }
                                    AppConstants.STREAK_TYPE_INDEFINITE -> {
                                        event.value = StreaksEvent.IndeifiniteClickedEvent
                                    }
                                }

                                streakMaxDays.set(streak.maxDuration.toString())

                                localPreferences.readValue(LocalPreferences.USER_ID)
                                    .collect { userId ->
                                        val participants =
                                            streak.participants?.filter { it?.userId != userId }

                                        val parts = mutableListOf<ActiveFriendsItem>()
                                        participants?.forEach {
                                            parts.add(
                                                ActiveFriendsItem(
                                                    countryCode = it?.countryCode,
                                                    userId = it?.userId,
                                                    name = it?.name,
                                                    profilePic = it?.profilePic,
                                                    mobileNumber = it?.mobileNumber
                                                )
                                            )
                                        }
                                        frindsInStreak.clear()
                                        frindsInStreak.addAll(parts)
                                        event.value = StreaksEvent.FriendAddedEvent
                                    }

                            }
                            is NetworkResponse.ApiError -> {
                                eventListener.dismissLoading()
                                eventListener.showMessageDialog(currentStreakDetail.error?.detail,
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

    }

    fun onIndefiniteClicked() {
        event.value = StreaksEvent.IndeifiniteClickedEvent
        streakType.set(AppConstants.STREAK_TYPE_INDEFINITE)
        setGoalVisibility.set(View.GONE)
    }

    fun onDefiniteClicked() {
        event.value = StreaksEvent.DeifiniteClickedEvent
        streakType.set(AppConstants.STREAK_TYPE_DEFINITE)
        setGoalVisibility.set(View.VISIBLE)
    }

    fun onFriendClicked(value: AddFriendBSItemVM) {
        val fr = frindsInStreak.find { it.userId == value.friend.userId }
        if (fr == null) {
            frindsInStreak.add(
                value.friend
            )
        } else {
            fr.isDeleted = false
        }
        event.value = StreaksEvent.FriendAddedEvent
    }

    fun save() {
        viewModelScope.launch {
            eventListener.showLoading()
            if (selectedImageFileCompressed == null) {
                createOrUpdateStreakDetails(null)
            } else {
                uploadImage()
            }
        }
    }

    suspend fun createOrUpdateStreakDetails(imgUrl: String?) {
        if (streakId == null || streakId == -1) {
            val friendsToAdd = mutableListOf<Int>()
            frindsInStreak.forEach {
                if (!it.isDeleted) {
                    friendsToAdd.add(it.userId!!)
                }
            }
            val saveStreakRequest = CreateStreakRequest(
                name = streakName.get(),
                type = streakType.get(),
                maxDuration = if (streakMaxDays.get().isNullOrBlank()) 0 else streakMaxDays.get()
                    ?.toInt(),
                startDate = streakStartDate,
                friends = friendsToAdd,
                streakImage = imgUrl
            )

            val apiResponse = commonRepository.createStreak(saveStreakRequest)
            when (apiResponse) {
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    eventListener.showMessageDialog("Saved Successfully",
                        "Success", "Ok", positiveClick = {
                            eventListener.dismissMessageDialog()
                            eventListener.closeActivity(true)
                        })
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
        } else {
            val frs = mutableListOf<FriendsRecordItem>()
            frindsInStreak.forEach {
                frs.add(FriendsRecordItem(isDeleted = it.isDeleted, userId = it.userId))
            }
            val updateStreakRequest = UpdateStreakRequest(
                name = streakName.get(),
                type = streakType.get(),
                maxDuration = if (streakMaxDays.get().isNullOrBlank()) 0 else streakMaxDays.get()
                    ?.toInt(),
                friendsRecord = frs,
                streakImage = imgUrl
            )
            val apiResponse =
                commonRepository.updateStreak(updateStreakRequest, streakId.toString())
            when (apiResponse) {
                is NetworkResponse.Success -> {
                    eventListener.dismissLoading()
                    eventListener.showMessageDialog("Saved Successfully",
                        "Success", "Ok", positiveClick = {
                            eventListener.dismissMessageDialog()
                            eventListener.closeActivity(true)
                        })
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
    }

    fun deleteStreak(){
        if (streakId != null || streakId != -1) {
            eventListener.showMessageDialog(
                title = "Streakify",
                message = "Are you sure you want to delete the streak",
                positiveClickTitle = "Yes",
                negativeClickTitle = "No",
                positiveClick = {
                    eventListener.dismissMessageDialog()
                    eventListener.showLoading()
                    viewModelScope.launch {
                        val apiResponse = commonRepository.deleteStreak(streakId.toString())
                        when(apiResponse){
                            is NetworkResponse.Success -> {
                                eventListener.dismissLoading()

                                eventListener.showMessageDialog(
                                    title = "Streakify",
                                    message = "Streak deleted successfully",
                                    positiveClick = {
                                        eventListener.dismissMessageDialog()
                                        event.value = StreaksEvent.StreakDeleted
                                    }
                                )
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
                },
                negativeClick = {
                    eventListener.dismissMessageDialog()
                }
            )
        }
    }

    private fun uploadImage() {
        if (selectedImageFileCompressed != null) {
            viewModelScope.launch {
                val image: RequestBody =
                    selectedImageFileCompressed!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                val apiResponse = commonRepository.pushImagesData(image)

                when (apiResponse) {
                    is NetworkResponse.Success -> {
                        val imgUrl = apiResponse.body?.body?.image

                        imgUrl?.let {
                            createOrUpdateStreakDetails(imgUrl)
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