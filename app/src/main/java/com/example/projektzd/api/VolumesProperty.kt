
package com.example.projektzd.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VolumesProperty(
        val totalItems: Int = 0,
        val items: MutableList<ItemsProperty> = mutableListOf()

) : Parcelable