package com.example.projektzd.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM bookshelf")
    fun getLibraryBooks(): List<BookEntity>

    @Query("SELECT * FROM bookshelf WHERE id = :id")
    fun getById(id: String): BookEntity

    @Query("SELECT * FROM api_bookshelf WHERE title LIKE :query")
    fun getSelectedBooks(query: String): List<ApiBookEntity>

    @Query("UPDATE bookshelf SET favorite = :favourite WHERE id = :id")
    fun updateFavourite(id: String, favourite: Int)

    @Query("UPDATE bookshelf SET read = :read WHERE id = :id")
    fun updateRead(id: String, read: Int)

    @Query("UPDATE bookshelf SET rentalDate = :rentalDate, returnDate = :returnDate WHERE id = :id")
    fun updateBook(id: String, rentalDate: String, returnDate: String)


    @Delete
    fun deleteBook(book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLibraryBooks(vararg book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertApiBooks(vararg book: ApiBookEntity)
}