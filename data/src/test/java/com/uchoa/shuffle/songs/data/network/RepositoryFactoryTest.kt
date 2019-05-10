package com.uchoa.shuffle.songs.data.network

import com.uchoa.shuffle.songs.data.network.http.HttpRepoImp
import com.uchoa.shuffle.songs.data.network.retrofit.RetrofitRepoImp
import org.junit.Assert
import org.junit.Test

class RepositoryFactoryTest {

    @Test
    fun `test return from factory when use pass http type`() {
        Assert.assertTrue(RepositoryFactory.getRepository(RepositoryType.HTTP) is HttpRepoImp)
    }

    @Test
    fun `test return from factory when use pass retrofit type`() {
        Assert.assertTrue(RepositoryFactory.getRepository(RepositoryType.RETROFIT) is RetrofitRepoImp)
    }
}