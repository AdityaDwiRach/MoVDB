package com.adr.movdb.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.adr.movdb.helper.session.Session
import com.adr.movdb.helper.session.SessionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefModule {

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefApp", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSession(sharedPreferences: SharedPreferences): Session {
        return SessionImpl(sharedPreferences)
    }
}