package com.uchoa.shuffle.songs.ui.home

import com.uchoa.shuffle.songs.data.network.RepositoryFactory
import com.uchoa.shuffle.songs.data.network.RepositoryType
import com.uchoa.shuffle.songs.domain.callbacks.LoadArtistsCallback
import com.uchoa.shuffle.songs.domain.entities.ArtistEntity
import com.uchoa.shuffle.songs.domain.entities.SongEntity
import com.uchoa.shuffle.songs.domain.usecases.LoadArtistsUseCase
import com.uchoa.shuffle.songs.utils.SongUtil
import java.util.*

class HomePresenter(
    var view: HomeContract.View?
) : HomeContract.Presenter {

    private var useCase: LoadArtistsUseCase
    private var loading: Boolean = false
    private var artistsCache = mutableListOf<ArtistEntity>()

    init {
        /**
         * Note: Cleiton Uchoa
         * Switching between repositories just for fun! :)
         */
        val random = Random()

        useCase = if (random.nextInt(2) == 1) {
            LoadArtistsUseCase(RepositoryFactory.getRepository(RepositoryType.HTTP))
        } else {
            LoadArtistsUseCase(RepositoryFactory.getRepository(RepositoryType.RETROFIT))
        }
    }

    override fun getSongs() {
        loading = true
        useCase.start(object : LoadArtistsCallback {
            override fun onSuccess(result: MutableList<ArtistEntity>) {
                artistsCache = result

                val songs = mutableListOf<SongEntity>()
                for (artist in artistsCache) {
                    songs.addAll(artist.songs)
                }

                view?.showSongs(songs)
                loading = false
            }

            override fun onError() {
                view?.showError()
            }
        })
    }

    override fun shuffle() {
        val artists = getCopyFromCache()

        if (artists.size > 1) {
            val shuffleSongs = SongUtil.shuffle(artists)
            view?.showSongs(shuffleSongs)
        }
    }

    override fun isLoadingSongs(): Boolean {
        return loading
    }

    override fun onDestroy() {
        view = null
    }

    private fun getCopyFromCache(): MutableList<ArtistEntity> {
        val copyList = mutableListOf<ArtistEntity>()
        for (artist in artistsCache) {
            val newArtist = artist.copy()
            newArtist.songs = artist.songs.toMutableList()
            copyList.add(newArtist)
        }
        return copyList
    }
}