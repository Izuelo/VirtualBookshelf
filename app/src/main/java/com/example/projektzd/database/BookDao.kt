package com.example.projektzd.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM api_bookshelf")
    fun getAllBooks(): LiveData<List<ApiBookEntity>>

    @Query("SELECT * FROM api_bookshelf WHERE title LIKE :query")
    fun getSelectedBooks(query: String): List<ApiBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLibraryBooks(vararg book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertApiBooks(vararg book: ApiBookEntity)
}