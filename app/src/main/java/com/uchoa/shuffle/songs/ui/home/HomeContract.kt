package com.uchoa.shuffle.songs.ui.home

import com.uchoa.shuffle.songs.domain.entities.SongEntity

interface HomeContract {

    interface View {
        fun showError()
        fun showEmptyView()
        fun showSongs(songs: MutableList<SongEntity>)
    }

    interface Presenter {
        fun getSongs()
        fun isLoadingSongs(): Boolean
        fun onDestroy()
        fun shuffle()
    }
}