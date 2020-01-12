package com.example.projektzd

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VolumesProperty(
    val totalItems: Int? = null,
    val items: List<ItemsProperty>? = null
) : Parcelable {

}