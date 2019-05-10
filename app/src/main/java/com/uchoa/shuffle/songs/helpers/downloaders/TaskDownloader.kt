package com.uchoa.shuffle.songs.helpers.downloaders

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.uchoa.shuffle.songs.helpers.ImageDownloader

class TaskDownloader private constructor() : ImageDownloader {

    var memoryCache = HashMap<String, Bitmap>()

    override fun loadImage(imageView: ImageView, imagePath: String) {
        Log.e("ImageDownloader", "download image using AsyncTask")
        if (memoryCache.containsKey(imagePath)) {
            imageView.setImageBitmap(memoryCache[imagePath]!!)
        } else {
            val task = ImageTask(imageView)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imagePath)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class ImageTask(private var image: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageUrl = urls[0]
            var bmp: Bitmap? = null
            try {
                val inputStream = java.net.URL(imageUrl).openStream()
                bmp = BitmapFactory.decodeStream(inputStream)
                memoryCache[imageUrl] = bmp
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bmp
        }

        override fun onPostExecute(bitmap: Bitmap) {
            image.setImageBitmap(bitmap)
        }
    }

    companion object {

        private var downloader: TaskDownloader? = null

        val instance: TaskDownloader
            get() {
                if (downloader == null) {
                    downloader = TaskDownloader()
                }

                return downloader as TaskDownloader
            }
    }
}