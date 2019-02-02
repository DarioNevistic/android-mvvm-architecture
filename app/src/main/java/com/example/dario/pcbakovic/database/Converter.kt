package com.example.dario.pcbakovic.database

import android.arch.persistence.room.TypeConverter
import com.example.dario.pcbakovic.database.entity.Item
import com.google.gson.Gson
import java.util.*


class Converter {

    @TypeConverter
    fun fromListToString(list: List<Item>?): String? {
        return if (list == null) null else Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(value: String?): List<Item>? {
        return Arrays.asList<Item>(*Gson().fromJson(value, Array<Item>::class.java))
    }

}