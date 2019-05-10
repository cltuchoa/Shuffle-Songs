package com.uchoa.shuffle.songs.helpers.downloaders

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.uchoa.shuffle.songs.helpers.ImageDownloader

class PicassoDownloader : ImageDownloader {

    override fun loadImage(imageView: ImageView, imagePath: String) {
        Log.d("ImageDownloader", "download image using Picasso")
        Picasso.get().load(imagePath).into(imageView)
    }

    companion object {

        private var downloader: PicassoDownloader? = null

        val instance: PicassoDownloader
            get() {
                if (downloader == null) {
                    downloader = PicassoDownloader()
                }

                return downloader as PicassoDownloader
            }
    }
}