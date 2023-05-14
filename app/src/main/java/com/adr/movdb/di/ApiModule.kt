package com.adr.movdb.di

import com.adr.movdb.model.api.ConfigurationApi
import com.adr.movdb.model.api.MovieDetailApi
import com.adr.movdb.model.api.MovieDiscoverApi
import com.adr.movdb.model.api.MovieGenreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideConfigurationApi(retrofit: Retrofit): ConfigurationApi {
        return retrofit.create(ConfigurationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailApi(retrofit: Retrofit): MovieDetailApi {
        return retrofit.create(MovieDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDiscoverApi(retrofit: Retrofit): MovieDiscoverApi {
        return retrofit.create(MovieDiscoverApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieGenreApi(retrofit: Retrofit): MovieGenreApi {
        return retrofit.create(MovieGenreApi::class.java)
    }
}