package com.example.projektzd.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsProperty(
    val id: String = "none",
    val volumeInfo: VolumeInfo

) : Parcelable