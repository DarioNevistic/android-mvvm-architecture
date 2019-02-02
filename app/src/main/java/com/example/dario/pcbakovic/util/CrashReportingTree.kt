package com.example.dario.pcbakovic.util

import android.util.Log
import timber.log.Timber


/**
 * Timber log tree for production.
 */
class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        when (priority) {
            Log.VERBOSE, Log.DEBUG, Log.INFO -> {
                return
            }
        }
    }


}