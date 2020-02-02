package com.example.projektzd.fragments.libraryBookFragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.projektzd.GlobalApplication
import com.example.projektzd.R
import com.example.projektzd.database.BookDatabase
import com.example.projektzd.database.BookEntity
import com.example.projektzd.databinding.FragmentEntityBinding
import java.text.SimpleDateFormat
import java.util.*

class LibraryBookFragment(
    private val book: BookEntity,
    private val supportFragmentManager: FragmentManager
) :
    Fragment() {

    lateinit var binding: FragmentEntityBinding
    private var rentalDateString: String = " "
    private var returnDateString: String = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_entity, container, false)

        binding.bookTitle.text = book.title
        binding.pageCount.text = book.numberOfPages.toString()
        binding.rentalDateTxt.text = book.rentalDate
        binding.returnDateTxt.text = book.returnDate
        binding.author.text = book.authors
        binding.description.text = book.description
        rentalDateString = book.rentalDate
        returnDateString = book.returnDate

        val imgUrl = book.thumbnail?.replace("http://", "https://")
        imgUrl.let {
            val imgUri = imgUrl?.toUri()?.buildUpon()?.build()
            Glide.with(GlobalApplication.appContext!!).load(imgUri)
                .fitCenter()
                .centerCrop()
                .into(binding.bookThumbnail)
        }

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = LibraryBookViewModelFactory(dataSource, supportFragmentManager, book)
        val libraryBookViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LibraryBookViewModel::class.java)
        binding.fragmentLibraryBookViewModel = libraryBookViewModel

        libraryBookViewModel.updateImageViews(binding.fillHeart, binding.readView)

        binding.fillHeart.setOnClickListener {
            libraryBookViewModel.addToFavourite(binding.fillHeart)
        }
        binding.readView.setOnClickListener {
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Do you want to change status of your book?")
                    .setMessage(book.title)
                    .setPositiveButton("YES") { _, _ ->
                        libraryBookViewModel.addToRead(binding.readView)
                    }
                    .setNegativeButton("No") { _, _ ->
                        showToast("Nothing has changed")
                    }
                    .create()
                    .show()
            }

        }
        binding.deleteBtn.setOnClickListener {
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Do you want to delete this book")
                    .setMessage(book.title)
                    .setPositiveButton("YES") { _, _ ->
                        libraryBookViewModel.handleDelete()
                    }
                    .setNegativeButton("No") { _, _ ->
                        showToast("Nothing has changed")
                    }
                    .create()
                    .show()
            }
        }
        binding.updateBtn.setOnClickListener {
            libraryBookViewModel.handleUpdate(rentalDateString, returnDateString)
        }

        handlePickDate()

        libraryBookViewModel.isDeletedObserver.observe(this, Observer {
            it.let {
                if (it > 0) {
                    supportFragmentManager.popBackStack()
                    showToast("${book.title} was removed")
                } else
                    showToast("There was a problem while removing ${book.title}")
            }
        })

        libraryBookViewModel.isReadObserver.observe(this, Observer {
            it.let {
                if (it > 0) {
                    showToast("${book.title} read status updated")
                } else
                    showToast("There was a problem while updating ${book.title}")
            }
        })

        libraryBookViewModel.isUpdatedObserver.observe(this, Observer {
            it.let {
                if (it > 0) {
                    supportFragmentManager.popBackStack()
                    showToast("Date has changed")
                } else
                    showToast("There was a problem while updating ${book.title}")
            }
        })

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun handlePickDate() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("dd-MM-yyyy")

        handleRentalDate(c, year, month, day, sdf)
        handleReturnDate(c, year, month, day, sdf)
    }

    private fun handleRentalDate(
        c: Calendar,
        year: Int,
        month: Int,
        day: Int,
        sdf: SimpleDateFormat
    ) {
        binding.rentalDateBtn.setOnClickListener {
            activity?.let {
                val dpd = DatePickerDialog(
                    it,
                    DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                        c.set(Calendar.YEAR, mYear)
                        c.set(Calendar.MONTH, mMonth)
                        c.set(Calendar.DAY_OF_MONTH, mDay)

                        rentalDateString = sdf.format(c.time)
                        binding.rentalDateTxt.text = rentalDateString
                    },
                    year,
                    month,
                    day
                )
                dpd.datePicker.maxDate = System.currentTimeMillis()
                dpd.show()
            }
        }
    }

    private fun handleReturnDate(
        c: Calendar,
        year: Int,
        month: Int,
        day: Int,
        sdf: SimpleDateFormat
    ) {
        binding.returnDateBtn.setOnClickListener {
            activity?.let {
                val dpd = DatePickerDialog(
                    it,
                    DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                        c.set(Calendar.YEAR, mYear)
                        c.set(Calendar.MONTH, mMonth)
                        c.set(Calendar.DAY_OF_MONTH, mDay)

                        returnDateString = sdf.format(c.time)
                        binding.returnDateTxt.text = returnDateString
                    },
                    year,
                    month,
                    day
                )
                dpd.datePicker.minDate = System.currentTimeMillis()
                dpd.show()
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}