package com.example.projektzd.fragments.libraryBookFragment

import androidx.lifecycle.MutableLiveData
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.projektzd.R
import com.example.projektzd.database.BookDao
import com.example.projektzd.database.BookEntity
import kotlinx.coroutines.*
import java.lang.Exception

class LibraryBookViewModel(
    val database: BookDao,
    val supportFragmentManager: FragmentManager,
    val book: BookEntity
) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    var isDeletedObserver = MutableLiveData<Int>()
    var isReadObserver = MutableLiveData<Int>()
    var isUpdatedObserver = MutableLiveData<Int>()

    fun updateImageViews(fillHeart: ImageView, readView: ImageView) {
        coroutineScope.launch {
            val dbBook = database.getById(book.id)

            if (dbBook.read == 0)
                readView.setImageResource(R.drawable.unread_icon)
            else
                readView.setImageResource(R.drawable.read_icon)

            if (dbBook.favorite == 0)
                fillHeart.setImageResource(R.drawable.unfilledfavorite)
            else
                fillHeart.setImageResource(R.drawable.favorite_fill)
        }
    }

    fun addToFavourite(imageView: ImageView) {
        coroutineScope.launch {
            try {
                val book = database.getById(book.id)

                if (book.favorite == 0) {
                    val row = database.updateFavourite(book.id, 1)
                    if (row > 0)
                        imageView.setImageResource(R.drawable.favorite_fill)
                } else {
                    val row = database.updateFavourite(book.id, 0)
                    if (row > 0)
                        imageView.setImageResource(R.drawable.unfilledfavorite)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToRead(imageView: ImageView) {
        coroutineScope.launch {
            try {
                val book = database.getById(book.id)
                val row: Int
                if (book.read == 0) {
                    row = database.updateRead(book.id, 1)
                    if (row > 0)
                        imageView.setImageResource(R.drawable.read_icon)
                } else {
                    row = database.updateRead(book.id, 0)
                    if (row > 0)
                        imageView.setImageResource(R.drawable.unread_icon)
                }
                isReadObserver.postValue(row)
            } catch (e: Exception) {
                Log.i("addToRead", e.toString())
            }
        }
    }

    fun handleDelete() {
        coroutineScope.launch {
            try {
                val dbBook = database.getById(book.id)
                val row = database.deleteBook(dbBook)
                isDeletedObserver.postValue(row)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun handleUpdate(rentalDateString: String, returnDateString: String) {
        coroutineScope.launch {
            try {
                val row = database.updateBook(
                    book.id,
                    rentalDateString,
                    returnDateString
                )
                isUpdatedObserver.postValue(row)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

