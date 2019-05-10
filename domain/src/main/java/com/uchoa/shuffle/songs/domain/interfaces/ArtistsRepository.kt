package com.uchoa.shuffle.songs.domain.interfaces

import com.uchoa.shuffle.songs.domain.callbacks.LoadArtistsCallback

interface ArtistsRepository {
    fun loadArtists(callback: LoadArtistsCallback)
}