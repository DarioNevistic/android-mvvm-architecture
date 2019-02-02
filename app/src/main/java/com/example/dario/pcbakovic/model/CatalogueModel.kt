package com.example.dario.pcbakovic.model

data class CatalogueModel(var images: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatalogueModel

        if (!images.contentEquals(other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        return images.contentHashCode()
    }
}