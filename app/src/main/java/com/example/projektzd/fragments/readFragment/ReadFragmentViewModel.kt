package com.example.projektzd.fragments.readFragment

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

class ReadFragmentViewModel(
    val database: BookDao
) : ViewModel() {
    private var list = LinkedList<BookEntity>()
    var mutableBooksList = MutableLiveData<List<BookEntity>>(list)
    var modifiedBooksList: LiveData<List<BookEntity>> = database.getLiveLibraryRead()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        getDatabaseLibrary()
    }

    private fun getDatabaseLibrary() {
        coroutineScope.launch {
            try {
                val booksList = database.getLibraryRead()
                list.clear()
                list.addAll(booksList)
                mutableBooksList.postValue(booksList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}