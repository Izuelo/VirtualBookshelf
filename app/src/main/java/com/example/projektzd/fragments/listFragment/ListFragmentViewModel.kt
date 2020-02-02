package com.example.projektzd.fragments.listFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projektzd.database.BookDao
import com.example.projektzd.database.BookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ListFragmentViewModel(
    val database: BookDao
) : ViewModel() {
    private var list = LinkedList<BookEntity>()
    var mutableBooksList = MutableLiveData<List<BookEntity>>(list)
    var modifiedBooksList: LiveData<List<BookEntity>> = database.getLiveLibraryBooks()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    var isFavourite = false

    init {
        getDatabaseLibrary()
    }

    private fun getDatabaseLibrary() {
        coroutineScope.launch {
            try {
                val booksList = database.getLibraryBooks()
                list.clear()
                list.addAll(booksList)
                mutableBooksList.postValue(booksList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDatabaseFavourites() {
        if (!isFavourite) {
            isFavourite = true
            coroutineScope.launch {
                try {
                    val booksList = database.getLibraryFavourites()
                    list.clear()
                    list.addAll(booksList)
                    mutableBooksList.postValue(booksList)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            isFavourite = false
            getDatabaseLibrary()
        }
    }
}