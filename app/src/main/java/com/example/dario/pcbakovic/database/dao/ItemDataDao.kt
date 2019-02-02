package com.example.dario.pcbakovic.database.dao

import android.arch.persistence.room.*
import com.example.dario.pcbakovic.database.entity.Item
import io.reactivex.Single

@Dao
interface ItemDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(apps: List<Item>)

    @Delete
    fun deleteItem(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Query("DELETE FROM item_table WHERE name = :name")
    fun deleteItemByName(name: String)

    @Update
    fun updateItem(item: Item)

    @Query("SELECT * FROM item_table")
    fun getShoppingItems(): Single<List<Item>>
}