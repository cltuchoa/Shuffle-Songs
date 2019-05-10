package com.uchoa.shuffle.songs.helpers

import android.widget.ImageView

interface ImageDownloader {

    fun loadImage(imageView: ImageView, imagePath: String)

}