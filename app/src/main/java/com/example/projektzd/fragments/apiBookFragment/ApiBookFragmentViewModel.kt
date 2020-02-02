package com.example.projektzd.fragments.apiBookFragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projektzd.database.ApiBookEntity
import com.example.projektzd.database.BookDao
import com.example.projektzd.database.BookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ApiBookFragmentViewModel(
    val database: BookDao,
    val supportFragmentManager: FragmentManager
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    var isInsertedObserver = MutableLiveData<Int>()

    fun addBook(
        book: ApiBookEntity,
        rentalDateString: String,
        returnDateString: String
    ) {
        coroutineScope.launch {
            try {
                var author = "NO AUTHOR FOUND"
                if (book.authors!!.isNotEmpty())
                    author = book.authors

                val bookEntity = BookEntity(
                    id = book.id,
                    title = book.title,
                    rentalDate = rentalDateString,
                    returnDate = returnDateString,
                    numberOfPages = book.numberOfPages,
                    thumbnail = book.thumbnail,
                    authors = author,
                    description = book.description
                )

                database.insertLibraryBooks(bookEntity).subscribe({
                    isInsertedObserver.postValue(1)
                }, {
                    isInsertedObserver.postValue(2)
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

