package com.example.dario.pcbakovic.di

import android.content.Context
import com.example.dario.pcbakovic.BakovicApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: BakovicApplication): Context {
        return application.applicationContext
    }
}