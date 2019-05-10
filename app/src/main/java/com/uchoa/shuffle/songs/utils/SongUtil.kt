package com.uchoa.shuffle.songs.utils

import com.uchoa.shuffle.songs.domain.entities.ArtistEntity
import com.uchoa.shuffle.songs.domain.entities.SongEntity
import java.util.*

class SongUtil {

    companion object {
        fun shuffle(artists: MutableList<ArtistEntity>): MutableList<SongEntity> {

            val shuffleSongs = mutableListOf<SongEntity>()

            val random = Random()
            var lastArtistIndex = -1

            while (artists.size > 0) {

                val artistIndex = random.nextInt(artists.size)

                if (lastArtistIndex != artistIndex) {
                    val song = artists[artistIndex].songs.removeAt(random.nextInt(artists[artistIndex].songs.size))
                    shuffleSongs.add(song)

                    lastArtistIndex = artistIndex
                    if (artists[artistIndex].songs.size == 0) {
                        artists.removeAt(artistIndex)
                    }
                } else if (artists.size == 1) {
                    for (song in artists.removeAt(0).songs) {
                        for (index in 0 until shuffleSongs.size - 1) {
                            if (shuffleSongs[index].artistId != song.artistId && shuffleSongs[index + 1].artistId != song.artistId) {
                                shuffleSongs.add(index + 1, song)
                                break
                            }
                        }
                    }
                }
            }

            return shuffleSongs
        }
    }
}