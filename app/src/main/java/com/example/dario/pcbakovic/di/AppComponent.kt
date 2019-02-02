package com.example.dario.pcbakovic.di

import com.example.dario.pcbakovic.BakovicApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    ViewModelModule::class,
    NetworkModule::class,
    RoomModule::class,
    SharedPreferencesModule::class,
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class])
interface AppComponent : AndroidInjector<BakovicApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<BakovicApplication>()
}