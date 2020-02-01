package com.example.projektzd.fragments.bookFragments

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projektzd.database.BookDao

class BookFragmentViewModelFactory(
    private val dataSource: BookDao,
    private val supportFragmentManager: FragmentManager
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookFragmentViewModel::class.java)) {
            return BookFragmentViewModel(dataSource, supportFragmentManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}