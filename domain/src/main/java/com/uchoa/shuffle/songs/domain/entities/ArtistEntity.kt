package com.uchoa.shuffle.songs.domain.entities

data class ArtistEntity(
    var id: Int = 0,
    var name: String = "",
    var songs: MutableList<SongEntity> = mutableListOf()
)