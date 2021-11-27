package com.ilhmdhn.movieapps.di

import android.content.Context
import com.ilhmdhn.movieapps.data.MovieRepository
import com.ilhmdhn.movieapps.data.source.local.LocalDataSource
import com.ilhmdhn.movieapps.data.source.local.room.ShowDatabase
import com.ilhmdhn.movieapps.data.source.remote.RemoteDataSource
import com.ilhmdhn.movieapps.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {

        val database = ShowDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataStorage = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataStorage, appExecutors)
    }
}