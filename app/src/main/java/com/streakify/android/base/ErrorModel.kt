package com.streakify.android.base

import com.google.gson.annotations.SerializedName

class ErrorModel : BaseModel() {

    @SerializedName("data")
    var data: HashMap<String, String>? = null
}