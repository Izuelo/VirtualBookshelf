package com.example.projektzd

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsProperty(
    val id: String? = null,
    val volumeInfo: VolumeInfo? = null
) : Parcelable {

}