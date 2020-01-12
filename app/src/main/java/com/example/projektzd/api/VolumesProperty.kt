package com.example.projektzd.api

import android.os.Parcelable
import androidx.lifecycle.LiveData
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class VolumesProperty(
    val totalItems: Int? = null,
    val items: @RawValue LiveData<List<ItemsProperty>>? = null

) : Parcelable