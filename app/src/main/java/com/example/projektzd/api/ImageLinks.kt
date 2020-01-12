package com.example.projektzd.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null

) : Parcelable {

}