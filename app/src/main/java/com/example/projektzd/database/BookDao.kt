package com.example.projektzd.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable

@Dao
interface BookDao {
    @Query("SELECT * FROM bookshelf WHERE read = 0")
    fun getLibraryBooks(): List<BookEntity>

    @Query("SELECT * FROM bookshelf WHERE read = 0")
    fun getLiveLibraryBooks(): LiveData<List<BookEntity>>

    @Query("SELECT * FROM bookshelf WHERE favorite = 1")
    fun getLibraryFavourites(): List<BookEntity>

    @Query("SELECT * FROM bookshelf WHERE read = 1")
    fun getLibraryRead(): List<BookEntity>

    @Query("SELECT * FROM bookshelf WHERE read = 1")
    fun getLiveLibraryRead(): LiveData<List<BookEntity>>

    @Query("SELECT * FROM bookshelf WHERE id = :id")
    fun getById(id: String): BookEntity

    @Query("SELECT * FROM api_bookshelf WHERE title LIKE :query")
    fun getSelectedBooks(query: String): List<ApiBookEntity>

    @Query("UPDATE bookshelf SET favorite = :favourite WHERE id = :id")
    fun updateFavourite(id: String, favourite: Int): Int

    @Query("UPDATE bookshelf SET read = :read WHERE id = :id")
    fun updateRead(id: String, read: Int): Int

    @Query("UPDATE bookshelf SET rentalDate = :rentalDate, returnDate = :returnDate WHERE id = :id")
    fun updateBook(id: String, rentalDate: String, returnDate: String): Int

    @Delete
    fun deleteBook(book: BookEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLibraryBooks(vararg book: BookEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertApiBooks(vararg book: ApiBookEntity)
}