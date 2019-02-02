package com.example.dario.pcbakovic.model

import com.google.firebase.firestore.PropertyName

//data class ItemModel (var title: String, var url: String)

data class ItemModel(
    @PropertyName("title") val title: String,
    @PropertyName("url") val url: String
) {
    constructor() : this("", "")
}