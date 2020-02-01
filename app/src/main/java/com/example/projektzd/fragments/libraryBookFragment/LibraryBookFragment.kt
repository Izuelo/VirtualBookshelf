package com.example.projektzd.fragments.libraryBookFragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.projektzd.GlobalApplication
import com.example.projektzd.R
import com.example.projektzd.database.BookDatabase
import com.example.projektzd.database.BookEntity
import com.example.projektzd.databinding.FragmentEntityBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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
            libraryBookViewModel.addToRead(binding.readView)
        }
        binding.deleteBtn.setOnClickListener {
            libraryBookViewModel.handleDelete()
        }
        binding.updateBtn.setOnClickListener {
            Log.i("XDD", "$rentalDateString   $returnDateString")
            libraryBookViewModel.handleUpdate(rentalDateString, returnDateString)
        }
        handlePickDate()

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

}