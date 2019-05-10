package com.uchoa.shuffle.songs.helpers

import com.uchoa.shuffle.songs.helpers.downloaders.GlideDownloader
import com.uchoa.shuffle.songs.helpers.downloaders.PicassoDownloader
import com.uchoa.shuffle.songs.helpers.downloaders.TaskDownloader
import org.junit.Assert.assertTrue
import org.junit.Test

class DownloaderFactoryTest {

    @Test
    fun `test return from factory when  use pass GLIDE type`() {
        assertTrue(DownloaderFactory.getDownloader(Downloaders.GLIDE) is GlideDownloader)
    }

    @Test
    fun `test return from factory when  use pass PICASSO type`() {
        assertTrue(DownloaderFactory.getDownloader(Downloaders.PICASSO) is PicassoDownloader)
    }

    @Test
    fun `test return from factory when  use pass ASYNC_TASK type`() {
        assertTrue(DownloaderFactory.getDownloader(Downloaders.ASYNC_TASK) is TaskDownloader)
    }
}