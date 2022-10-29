package com.example.rappitest.di

import android.content.Context
import androidx.room.Room
import com.example.rappitest.data.local.MoviesDatabase
import com.example.rappitest.data.repository.LocalDataSourceImpl
import com.example.rappitest.domain.repository.LocalDataSource
import com.example.rappitest.util.Constants.MOVIES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDataba(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MoviesDatabase::class.java,
        MOVIES_DATABASE
    ).build()

    @Provides
    @Singleton
    fun providesLocalDataSource(
        moviesDatabase: MoviesDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            moviesDatabase
        )
    }

}