package com.example.projektzd.fragments.libraryBookFragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projektzd.database.BookDao
import com.example.projektzd.database.BookEntity

class LibraryBookViewModelFactory(
    private val dataSource: BookDao,
    private val supportFragmentManager: FragmentManager,
    private val book: BookEntity
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryBookViewModel::class.java)) {
            return LibraryBookViewModel(dataSource, supportFragmentManager, book) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}