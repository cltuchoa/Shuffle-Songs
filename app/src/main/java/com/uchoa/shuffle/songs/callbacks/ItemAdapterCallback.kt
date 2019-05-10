package com.uchoa.shuffle.songs.callbacks

import com.uchoa.shuffle.songs.domain.entities.SongEntity

interface ItemAdapterCallback {
    fun onCellClick(song: SongEntity)
}