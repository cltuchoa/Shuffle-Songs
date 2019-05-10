package com.uchoa.shuffle.songs.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultResponse {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("artistId")
    @Expose
    var artistId: Int = 0

    @SerializedName("wrapperType")
    @Expose
    var wrapperType: String? = null

    @SerializedName("artistName")
    @Expose
    var artistName: String? = null

    @SerializedName("collectionName")
    @Expose
    var collectionName: String? = null

    @SerializedName("trackName")
    @Expose
    var trackName: String? = null

    @SerializedName("primaryGenreName")
    @Expose
    var genre: String? = null

    @SerializedName("artworkUrl")
    @Expose
    var artworkUrl: String? = null

    @SerializedName("trackTimeMillis")
    @Expose
    var trackTimeMillis: Long? = 0
}