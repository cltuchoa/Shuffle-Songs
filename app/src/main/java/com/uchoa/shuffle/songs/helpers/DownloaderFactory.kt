package com.uchoa.shuffle.songs.helpers

import com.uchoa.shuffle.songs.helpers.downloaders.GlideDownloader
import com.uchoa.shuffle.songs.helpers.downloaders.PicassoDownloader
import com.uchoa.shuffle.songs.helpers.downloaders.TaskDownloader

class DownloaderFactory {

    companion object {
        fun getDownloader(type: Downloaders): ImageDownloader {
            return when (type) {
                Downloaders.GLIDE -> GlideDownloader.instance
                Downloaders.ASYNC_TASK -> TaskDownloader.instance
                Downloaders.PICASSO -> PicassoDownloader.instance
            }
        }
    }
}