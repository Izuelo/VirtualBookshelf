package com.example.projektzd.fragments.searchFragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.projektzd.GlobalApplication
import com.example.projektzd.R
import com.example.projektzd.api.ItemsProperty
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentBookBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class BookFragment(
        private val book: ItemsProperty,
        private val supportFragmentManager: FragmentManager,
        val dbHelper: DatabaseHelper
) :
        Fragment() {

    lateinit var binding: FragmentBookBinding
    var rentalDateString: String = " "
    var returnDateString: String = " "


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false)

        binding.bookTitle.text = book.volumeInfo.title
        binding.pageCount.text = book.volumeInfo.pageCount.toString()
        if(book.volumeInfo.authors.isNotEmpty())
        binding.author.text = book.volumeInfo.authors[0]
        binding.description.text = book.volumeInfo.description

        val imgUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon()?.build()
            Glide.with(GlobalApplication.appContext!!).load(imgUri)
                    .fitCenter()
                    .centerCrop()
                    .into(binding.bookThumbnail)
        }
        handleInserts()
        handlePickDate()

        return binding.root
    }

    private fun handleInserts() {
        binding.addBtn.setOnClickListener {
            try {
                var author = " "
                if(book.volumeInfo.authors.isNotEmpty())
                 author = book.volumeInfo.authors[0]

                dbHelper.insertData(
                        book.id,
                        book.volumeInfo.title,
                        rentalDateString,
                        returnDateString,
                        calcRemainingDays(),
                        book.volumeInfo.pageCount,
                        book.volumeInfo.imageLinks?.thumbnail,
                        false,
                        false,
                        author,
                        book.volumeInfo.description

                )
                showToast("The book was added to Virtual Bookshelf")
                supportFragmentManager.popBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handlePickDate() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("dd-MM-yyyy")

        rentalDateString = sdf.format(Date())
        returnDateString = sdf.format(Date())
        binding.rentalDateTxt.text = rentalDateString
        binding.returnDateTxt.text = returnDateString

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
            activity?.let { it1 ->
               var dpd = DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
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
                dpd.datePicker.maxDate=System.currentTimeMillis()
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
            activity?.let { it1 ->
              var dpd = DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
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
                dpd.datePicker.minDate=System.currentTimeMillis()
                dpd.show()
            }

        }
    }

    private fun calcRemainingDays(): Int {
//        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//        val localDate: LocalDateTime = LocalDateTime.now()
//        val sysDate: LocalDate =
//            LocalDate.of(localDate.year, localDate.monthValue, localDate.dayOfMonth)
//
//        val valDate: LocalDate =
//            LocalDate.parse(returnDateString, formater)
//        return ChronoUnit.DAYS.between(sysDate, valDate).toInt()
        return 10
    }

    fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

}
