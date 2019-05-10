package com.uchoa.shuffle.songs.data.network.retrofit

import com.uchoa.shuffle.songs.data.Constants
import com.uchoa.shuffle.songs.data.models.DataResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServiceApi {

    @GET(Constants.DATA_PATH)
    fun getData(): Call<DataResponse>
}