package com.example.projektzd.database

data class Book(
    val id: String = "none",
    val title: String = "none",
    val rentalDate: String = "",
    val returnDate: String = "",
    var remainingDays: Int = 0,
    val numberOfPages: Int = 0,
    val thumbnail: String? = " ",
    val favorite: Int = 0,
    val read: Int = 0,
    val authors: String? = " ",
    val description: String? = " "
)