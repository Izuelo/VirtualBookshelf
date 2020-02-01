package com.example.projektzd.fragments.libraryBookFragment

import android.app.AlertDialog
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.projektzd.GlobalApplication
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
                    database.updateFavourite(book.id, 1)
                    imageView.setImageResource(R.drawable.favorite_fill)
                } else {
                    database.updateFavourite(book.id, 0)
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

                if (book.read == 0) {
                    database.updateRead(book.id, 1)
                    imageView.setImageResource(R.drawable.read_icon)
                } else {
                    AlertDialog.Builder(GlobalApplication.appContext)
                        .setTitle("Do you want to read this book again")
                        .setMessage(book.title)
                        .setPositiveButton("YES") { _, _ ->
                            database.updateRead(book.id, 0)
                            imageView.setImageResource(R.drawable.unread_icon)
//                            showToast("${book.title} is set as non read")
                        }
                        .setNegativeButton("No") { _, _ ->
                            //                            showToast("Nothing has changed")
                        }
                        .create()
                        .show()
                }
            } catch (e: Exception) {
                Log.i("addToRead", e.toString())
            }
        }
    }

    fun handleDelete() {
        coroutineScope.launch {
            try {
                val book = database.getById(book.id)
                database.deleteBook(book)
                supportFragmentManager.popBackStack()
//                AlertDialog.Builder(GlobalApplication.appContext)
//                    .setTitle("Do you want to delete this book")
//                    .setMessage(book.title)
//                    .setPositiveButton("YES") { _, _ ->
//                        database.deleteBook(book)
//                        supportFragmentManager.popBackStack()
////                        showToast("${book.title} was removed")
//                    }
//                    .setNegativeButton("No") { _, _ ->
////                        showToast("Nothing has changed")
//                    }
//                    .create()
//                    .show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun handleUpdate(rentalDateString: String, returnDateString: String) {
        coroutineScope.launch {
            try {
                database.updateBook(
                    book.id,
                    rentalDateString,
                    returnDateString
                )
//                showToast("Date has changed")
                supportFragmentManager.popBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(GlobalApplication.appContext, text, Toast.LENGTH_LONG).show()
    }
}