package com.example.projektzd.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VolumeInfo(
        val authors: MutableList<String> = mutableListOf(),
        val subtitle: String = "none",
        val title: String = "none",
        val publisher: String = "none",
        val publishedDate: String = "none",
        val pageCount: Int = 0,
        val description: String = "none",
        val imageLinks: ImageLinks? = null,
        val language: String = "none"

) : Parcelable

