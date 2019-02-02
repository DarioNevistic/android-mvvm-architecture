package com.example.dario.pcbakovic

import com.example.dario.pcbakovic.di.DaggerAppComponent
import com.example.dario.pcbakovic.util.Constants
import com.example.dario.pcbakovic.util.CrashReportingTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class BakovicApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, Constants.LOG_TAG, message, t)
                }
            })
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}