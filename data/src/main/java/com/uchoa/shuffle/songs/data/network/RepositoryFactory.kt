package com.uchoa.shuffle.songs.data.network

import com.uchoa.shuffle.songs.data.network.http.HttpRepoImp
import com.uchoa.shuffle.songs.data.network.retrofit.RetrofitRepoImp
import com.uchoa.shuffle.songs.domain.interfaces.ArtistsRepository

class RepositoryFactory {

    companion object {

        fun getRepository(type: RepositoryType): ArtistsRepository {
            return when (type) {
                RepositoryType.HTTP -> HttpRepoImp.instance
                RepositoryType.RETROFIT -> RetrofitRepoImp.instance
            }
        }
    }
}