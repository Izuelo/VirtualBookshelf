package com.example.projektzd.fragments.databaseFragments

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
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        getDatabaseResponse()
    }

    private fun getDatabaseResponse() {
        coroutineScope.launch {
            val booksList = database.getLibraryBooks()
            list.clear()
            list.addAll(booksList)
            mutableBooksList.postValue(booksList)
        }
    }
}