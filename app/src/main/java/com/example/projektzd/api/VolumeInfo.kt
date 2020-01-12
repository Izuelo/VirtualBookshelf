package com.example.projektzd.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VolumeInfo(
    val authors: List<String>? = null,
    val subtitle: String? = null,
    val title: String? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val pageCount: Int? = null,
    val description: String? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null

) : Parcelable {

}
