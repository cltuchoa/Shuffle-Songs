package com.uchoa.shuffle.songs.data.mappers

import com.uchoa.shuffle.songs.data.models.ResultResponse
import com.uchoa.shuffle.songs.domain.entities.ArtistEntity
import com.uchoa.shuffle.songs.domain.entities.SongEntity
import java.util.concurrent.TimeUnit

class ResponseMapper {

    fun getArtistsFrom(results: MutableList<ResultResponse>): MutableList<ArtistEntity> {
        val artists = mutableListOf<ArtistEntity>()

        var currentArtist: ArtistEntity? = null

        for (item in results) {
            if (item.wrapperType == "artist") {
                currentArtist = ArtistEntity(
                    item.id,
                    item.artistName ?: "",
                    mutableListOf()
                )
                artists.add(currentArtist)
            } else if (item.wrapperType == "track") {
                val song = SongEntity(
                    item.id,
                    item.artistId,
                    item.trackName ?: "",
                    item.genre ?: "",
                    item.artistName ?: "",
                    item.artworkUrl ?: "",
                    item.collectionName ?: "",
                    convertTrackTimeInString(item.trackTimeMillis ?: 0)
                )
                currentArtist?.songs?.add(song)
            }
        }

        return artists
    }

    private fun convertTrackTimeInString(millis: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }
}