package com.example.dario.pcbakovic.di

import android.content.Context
import com.example.dario.pcbakovic.database.AppDatabase
import com.example.dario.pcbakovic.database.dao.ItemDataDao
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object RoomModule {

    @Provides
    @Reusable
    @JvmStatic
    fun provideAppDatabase(context: Context) = AppDatabase.createPersistentDatabase(context)

    @Provides
    @Reusable
    @JvmStatic
    fun provideShoppingItemsDao(appDatabase: AppDatabase): ItemDataDao {
        return appDatabase.shoppingItemsDao()
    }
}