package com.uchoa.shuffle.songs.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse {

    @SerializedName("resultCount")
    @Expose
    var resultCount: Int = 0

    @SerializedName("results")
    @Expose
    var resultResponse: MutableList<ResultResponse>? = null
}