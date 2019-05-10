package com.uchoa.shuffle.songs.domain.entities

data class SongEntity(
    var id: Int = 0,
    var artistId: Int = 0,
    var name: String = "",
    var genre: String = "",
    var artist: String = "",
    var imagePath: String = "",
    var collectionName: String = "",
    var trackTime: String
)