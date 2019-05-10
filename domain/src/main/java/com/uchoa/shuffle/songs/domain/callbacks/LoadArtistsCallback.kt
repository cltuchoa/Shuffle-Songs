package com.uchoa.shuffle.songs.domain.callbacks

import com.uchoa.shuffle.songs.domain.entities.ArtistEntity

interface LoadArtistsCallback {

    fun onSuccess(result: MutableList<ArtistEntity>)

    fun onError()
}