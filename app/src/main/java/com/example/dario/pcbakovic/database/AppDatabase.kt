package com.example.dario.pcbakovic.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.dario.pcbakovic.database.dao.ItemDataDao
import com.example.dario.pcbakovic.database.entity.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shoppingItemsDao(): ItemDataDao

    companion object {
        private const val DB_NAME = "Runner.db"

        fun createPersistentDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .build()
    }
}