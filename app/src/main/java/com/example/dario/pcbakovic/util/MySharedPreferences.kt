package com.example.dario.pcbakovic.util

import android.content.SharedPreferences

import javax.inject.Inject


class MySharedPreferences @Inject
constructor(private val mSharedPreferences: SharedPreferences) {

    fun putString(key: String, data: String) {
        mSharedPreferences.edit().putString(key, data).apply()
    }

    fun getString(key: String): String? {
        return mSharedPreferences.getString(key, "")
    }

    fun removeString(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }
}
