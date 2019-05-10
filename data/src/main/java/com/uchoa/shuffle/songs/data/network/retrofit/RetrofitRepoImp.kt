package com.uchoa.shuffle.songs.data.network.retrofit

import android.support.annotation.NonNull
import android.util.Log
import com.uchoa.shuffle.songs.data.Constants
import com.uchoa.shuffle.songs.data.mappers.ResponseMapper
import com.uchoa.shuffle.songs.data.models.DataResponse
import com.uchoa.shuffle.songs.domain.callbacks.LoadArtistsCallback
import com.uchoa.shuffle.songs.domain.interfaces.ArtistsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepoImp private constructor(
    private val artistsServiceApi: RetrofitServiceApi,
    private val mapper: ResponseMapper = ResponseMapper()
) : ArtistsRepository {

    override fun loadArtists(callback: LoadArtistsCallback) {
        Log.d("ArtistsRepository", "Get data using RetrofitRepoImp")
        artistsServiceApi.getData()
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    @NonNull call: Call<DataResponse>,
                    @NonNull response: Response<DataResponse>
                ) {
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (moviesResponse?.resultResponse != null) {
                            callback.onSuccess(mapper.getArtistsFrom(moviesResponse.resultResponse!!))
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    companion object {

        private var repository: RetrofitRepoImp? = null

        val instance: RetrofitRepoImp
            get() {
                if (repository == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    repository = RetrofitRepoImp(retrofit.create(RetrofitServiceApi::class.java))
                }

                return repository as RetrofitRepoImp
            }
    }
}