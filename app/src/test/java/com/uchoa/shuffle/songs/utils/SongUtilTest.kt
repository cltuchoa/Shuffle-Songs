package com.uchoa.shuffle.songs.utils

import com.uchoa.shuffle.songs.domain.entities.ArtistEntity
import com.uchoa.shuffle.songs.domain.entities.SongEntity
import org.junit.Assert.assertTrue
import org.junit.Test

class SongUtilTest {

    @Test
    fun `test shuffle in not allow songs from same artist together`() {

        val artists = generateArtistList()
        var nextId = 0
        var previousId = 0

        val songs = SongUtil.shuffle(artists)

        for (index in 0 until songs.size) {

            if (nextId != 0) {
                assertTrue(songs[index].artistId != nextId)
            }

            if (previousId != 0) {
                assertTrue(songs[index].artistId != previousId)
            }

            previousId = songs[index].artistId
            nextId = if (index + 1 < songs.size) {
                songs[index].artistId
            } else {
                0
            }
        }
    }

    private fun generateArtistList(): MutableList<ArtistEntity> {

        val artists = mutableListOf<ArtistEntity>()

        for (count in 0 until 5) {
            val artist = ArtistEntity(count)
            artist.name = "Artist $count"

            val songs = mutableListOf<SongEntity>()
            songs.add(SongEntity(1, count))
            songs.add(SongEntity(2, count))
            songs.add(SongEntity(3, count))
            songs.add(SongEntity(4, count))
            songs.add(SongEntity(5, count))
            artist.songs = songs

            artists.add(artist)
        }

        return artists
    }
}
