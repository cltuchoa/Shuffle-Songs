package com.uchoa.shuffle.songs.domain.usecases

import com.uchoa.shuffle.songs.domain.callbacks.LoadArtistsCallback
import com.uchoa.shuffle.songs.domain.entities.ArtistEntity
import com.uchoa.shuffle.songs.domain.interfaces.ArtistsRepository
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito

class LoadArtistsUseCaseTest {

    @Test
    fun `test load artists use case when service return empty`() {
        val repository = Mockito.mock(ArtistsRepository::class.java)
        val callback = object : LoadArtistsCallback {
            override fun onSuccess(result: MutableList<ArtistEntity>) {
                assert(result.size == 0)
            }

            override fun onError() {
                assertTrue(false)
            }
        }

        Mockito.`when`(repository.loadArtists(callback)).then {
            callback.onSuccess(mutableListOf())
        }

        val artists = LoadArtistsUseCase(repository)
        artists.start(callback)
    }

    @Test
    fun `test load artists use case when service return data`() {
        val repository = Mockito.mock(ArtistsRepository::class.java)
        val callback = object : LoadArtistsCallback {
            override fun onSuccess(result: MutableList<ArtistEntity>) {
                assert(result.size == 5)
            }

            override fun onError() {
                assertTrue(false)
            }
        }

        Mockito.`when`(repository.loadArtists(callback)).then {
            callback.onSuccess(generateArtistList())
        }

        val artists = LoadArtistsUseCase(repository)
        artists.start(callback)
    }

    @Test
    fun `test load artists use case when service return error`() {

        val repository = Mockito.mock(ArtistsRepository::class.java)
        val callback = object : LoadArtistsCallback {
            override fun onSuccess(result: MutableList<ArtistEntity>) {
                assertTrue(false)
            }

            override fun onError() {
                assertTrue(true)
            }
        }

        Mockito.`when`(repository.loadArtists(callback)).then {
            callback.onError()
        }

        val artists = LoadArtistsUseCase(repository)
        artists.start(callback)
    }

    private fun generateArtistList(): MutableList<ArtistEntity> {
        val list = mutableListOf<ArtistEntity>()

        list.add(ArtistEntity(1))
        list.add(ArtistEntity(2))
        list.add(ArtistEntity(3))
        list.add(ArtistEntity(4))
        list.add(ArtistEntity(5))

        return list
    }
}