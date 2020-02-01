package com.example.projektzd.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class VolumesProperty<T>(
    val items: @RawValue List<T>
) : Parcelable