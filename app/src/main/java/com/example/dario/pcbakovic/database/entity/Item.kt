package com.example.dario.pcbakovic.database.entity

import android.arch.persistence.room.Entity

@Entity(tableName = "item_table", primaryKeys = ["name"])
data class Item(
    var name: String,

    var isDone : Boolean = false
)