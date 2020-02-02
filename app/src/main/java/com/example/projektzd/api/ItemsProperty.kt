package com.example.projektzd.api

import android.os.Parcelable
import com.example.projektzd.database.ApiBookEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsProperty(
    val id: String = "none",
    val volumeInfo: VolumeInfo

) : Parcelable

fun List<ItemsProperty>.toEntity(): List<ApiBookEntity> {
    return map {
        var authorsT = "NO AUTHOR"
        if (!it.volumeInfo.authors.isNullOrEmpty()) {
            authorsT = it.volumeInfo.authors.get(0)
        }
        ApiBookEntity(
            id = it.id,
            title = it.volumeInfo.title,
            numberOfPages = it.volumeInfo.pageCount,
            thumbnail = it.volumeInfo.imageLinks?.thumbnail,
            authors = authorsT,
            description = it.volumeInfo.description
        )
    }
}