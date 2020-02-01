package com.example.projektzd.fragments.bookFragments

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.example.projektzd.database.ApiBookEntity
import com.example.projektzd.database.BookDao
import com.example.projektzd.database.BookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class BookFragmentViewModel(
    val database: BookDao,
    val supportFragmentManager: FragmentManager
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun addBook(
        book: ApiBookEntity,
        rentalDateString: String,
        returnDateString: String,
        activity: FragmentActivity?
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
                    remainingDays = calcRemainingDays(returnDateString),
                    numberOfPages = book.numberOfPages,
                    thumbnail = book.thumbnail,
                    authors = author,
                    description = book.description
                )

                database.insertLibraryBooks(bookEntity)

//                showToast("The book was added to Virtual Bookshelf", activity)
                supportFragmentManager.popBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun calcRemainingDays(returnDateString: String): Int {
        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val localDate: LocalDateTime = LocalDateTime.now()
        val sysDate: LocalDate =
            LocalDate.of(localDate.year, localDate.monthValue, localDate.dayOfMonth)

        val valDate: LocalDate =
            LocalDate.parse(returnDateString, formater)
        return ChronoUnit.DAYS.between(sysDate, valDate).toInt()
    }

    fun showToast(text: String, activity: FragmentActivity?) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}

