package com.uchoa.shuffle.songs.data.network.http

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.uchoa.shuffle.songs.data.Constants
import com.uchoa.shuffle.songs.data.mappers.ResponseMapper
import com.uchoa.shuffle.songs.data.models.DataResponse
import com.uchoa.shuffle.songs.domain.callbacks.LoadArtistsCallback
import com.uchoa.shuffle.songs.domain.interfaces.ArtistsRepository
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class HttpRepoImp private constructor(
    private val mapper: ResponseMapper = ResponseMapper()
) : ArtistsRepository {

    override fun loadArtists(callback: LoadArtistsCallback) {
        Log.d("ArtistsRepository", "Get data using HttpRepoImp")

        val url = Constants.BASE_URL + Constants.DATA_PATH
        val task = RequestTask(callback)
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url)
    }

    inner class RequestTask(private var callback: LoadArtistsCallback) : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg urls: String): String? {
            val urlString = urls[0]

            val url: URL
            val responseJSON: String
            val response = StringBuffer()

            try {
                url = URL(urlString)
            } catch (e: MalformedURLException) {
                throw IllegalArgumentException("invalid url")
            }


            var conn: HttpURLConnection? = null

            try {
                conn = url.openConnection() as HttpURLConnection
                conn.doOutput = false
                conn.doInput = true
                conn.useCaches = false
                conn.requestMethod = "GET"
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")

                val status = conn.responseCode
                if (status != 200) {
                    throw IOException("Request failed : error code $status")
                } else {
                    val inputStreamReader = BufferedReader(InputStreamReader(conn.inputStream))

                    var inputLine: String?

                    inputLine = inputStreamReader.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = inputStreamReader.readLine()
                    }

                    inputStreamReader.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                conn?.disconnect()
                responseJSON = response.toString()
            }

            return responseJSON
        }

        override fun onPostExecute(responseJSON: String) {
            handleServerResponse(responseJSON)
        }

        private fun handleServerResponse(responseJSON: String) {
            val gson = Gson()
            if (responseJSON == "") {
                callback.onError()
            } else {
                val dataResponse = gson.fromJson(responseJSON, DataResponse::class.java) as DataResponse
                if (dataResponse.resultResponse != null) {
                    callback.onSuccess(mapper.getArtistsFrom(dataResponse.resultResponse!!))
                } else {
                    callback.onError()
                }
            }
        }
    }

    companion object {

        private var repo: HttpRepoImp? = null

        val instance: HttpRepoImp
            get() {
                if (repo == null) {
                    repo = HttpRepoImp()
                }

                return repo as HttpRepoImp
            }
    }
}