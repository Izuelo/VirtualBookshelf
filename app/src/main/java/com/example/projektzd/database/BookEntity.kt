package com.example.projektzd.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "bookshelf")
data class BookEntity(
    @PrimaryKey val id: String,

    val title: String,
    val rentalDate: String,
    val returnDate: String,
    val remainingDays: Int,
    val numberOfPages: Int,
    val thumbnail: String? = " ",
    val favorite: Int = 0,
    val read: Int = 0,
    val authors: String? = " ",
    val description: String? = " "
) : Parcelable

@Parcelize
@Entity(tableName = "api_bookshelf")
data class ApiBookEntity(
    @PrimaryKey val id: String,

    val title: String,
    val numberOfPages: Int,
    val thumbnail: String? = " ",
    val authors: String? = " ",
    val description: String? = " "
) : Parcelable