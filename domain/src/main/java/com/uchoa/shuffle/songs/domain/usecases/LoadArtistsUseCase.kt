package com.uchoa.shuffle.songs.domain.usecases

import com.uchoa.shuffle.songs.domain.callbacks.LoadArtistsCallback
import com.uchoa.shuffle.songs.domain.interfaces.ArtistsRepository

class LoadArtistsUseCase(private val repository: ArtistsRepository) {

    fun start(callBack: LoadArtistsCallback) {
        repository.loadArtists(callBack)
    }
}