package com.uchoa.shuffle.songs.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uchoa.shuffle.songs.data.models.DataResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileReader

class ResponseMapperTest {

    private lateinit var dataResponse: DataResponse
    private lateinit var responseMapper: ResponseMapper

    @Before
    fun before() {
        val path = "../data/src/test/java/com/uchoa/shuffle/songs/data/mocks/data.json"
        val type = object : TypeToken<DataResponse>() {}.type
        dataResponse = Gson().fromJson<DataResponse>(FileReader(path), type)
        responseMapper = ResponseMapper()
    }

    @Test
    fun `test response mapper when server return empty`() {
        val result = responseMapper.getArtistsFrom(mutableListOf())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `test response mapper when server return data`() {
        val artists = responseMapper.getArtistsFrom(dataResponse.resultResponse!!)
        assertEquals(artists.size, 1)
        assertEquals(artists[0].songs.size, 2)
    }

    @Test
    fun `test correct mapping between track and artist`() {
        val artists = responseMapper.getArtistsFrom(dataResponse.resultResponse!!)

        for (song in artists[0].songs) {
            assertEquals(artists[0].id, song.artistId)
            assertEquals(artists[0].name, song.artist)
        }
    }
}
