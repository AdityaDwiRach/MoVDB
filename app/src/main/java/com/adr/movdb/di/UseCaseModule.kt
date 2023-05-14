package com.adr.movdb.di

import com.adr.movdb.model.repository.ConfigurationRepository
import com.adr.movdb.model.repository.MovieDetailRepository
import com.adr.movdb.model.repository.MovieDiscoverRepository
import com.adr.movdb.model.repository.MovieGenreRepository
import com.adr.movdb.model.usecase.ConfigurationUseCases
import com.adr.movdb.model.usecase.DetailUseCases
import com.adr.movdb.model.usecase.DiscoverUseCases
import com.adr.movdb.model.usecase.GenreUseCases
import com.adr.movdb.model.usecase.configuration.GetConfigurationUseCase
import com.adr.movdb.model.usecase.detail.GetDetailMovieUseCase
import com.adr.movdb.model.usecase.detail.GetListReviewMovieUseCase
import com.adr.movdb.model.usecase.detail.GetListVideoMovieUseCase
import com.adr.movdb.model.usecase.discover.GetListMovieByGenreUseCase
import com.adr.movdb.model.usecase.genre.GetListGenreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideConfigurationUseCases(configurationRepository: ConfigurationRepository): ConfigurationUseCases {
        return ConfigurationUseCases(
            GetConfigurationUseCase(configurationRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDetailUseCases(movieDetailRepository: MovieDetailRepository): DetailUseCases {
        return DetailUseCases(
            GetDetailMovieUseCase(movieDetailRepository),
            GetListReviewMovieUseCase(movieDetailRepository),
            GetListVideoMovieUseCase(movieDetailRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDiscoverUseCases(movieDiscoverRepository: MovieDiscoverRepository): DiscoverUseCases {
        return DiscoverUseCases(GetListMovieByGenreUseCase(movieDiscoverRepository))
    }

    @Provides
    @Singleton
    fun provideGenreUseCases(movieGenreRepository: MovieGenreRepository): GenreUseCases {
        return GenreUseCases(GetListGenreUseCase(movieGenreRepository))
    }
}