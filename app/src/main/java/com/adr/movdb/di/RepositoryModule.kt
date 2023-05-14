package com.adr.movdb.di

import com.adr.movdb.model.repository.ConfigurationRepository
import com.adr.movdb.model.repository.ConfigurationRepositoryImpl
import com.adr.movdb.model.repository.MovieDetailRepository
import com.adr.movdb.model.repository.MovieDetailRepositoryImpl
import com.adr.movdb.model.repository.MovieDiscoverRepository
import com.adr.movdb.model.repository.MovieDiscoverRepositoryImpl
import com.adr.movdb.model.repository.MovieGenreRepository
import com.adr.movdb.model.repository.MovieGenreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindConfigurationRepository(configurationRepositoryImpl: ConfigurationRepositoryImpl): ConfigurationRepository

    @Binds
    @Singleton
    abstract fun bindMovieDetailRepository(movieDetailRepositoryImpl: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds
    @Singleton
    abstract fun bindMovieDiscoverRepository(
        movieDiscoverRepositoryImpl: MovieDiscoverRepositoryImpl
    ): MovieDiscoverRepository

    @Binds
    @Singleton
    abstract fun bindMovieGenreRepository(movieGenreRepositoryImpl: MovieGenreRepositoryImpl): MovieGenreRepository

}