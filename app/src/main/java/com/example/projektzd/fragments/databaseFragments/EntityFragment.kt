package com.example.projektzd.fragments.databaseFragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.projektzd.GlobalApplication
import com.example.projektzd.R
import com.example.projektzd.adapters.adapterDatabase.GetEntities
import com.example.projektzd.database.Book
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentEntityBinding
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class EntityFragment(
        private val book: Book,
        private val supportFragmentManager: FragmentManager,
        private val dbHelper: DatabaseHelper

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
        changeFav(binding.fillHeart)
        addToFavourite(binding.fillHeart)
        changeRead(binding.readView)
        addToRead(binding.readView)
        val imgUrl = book.thumbnail?.replace("http://", "https://")
        imgUrl.let {
            val imgUri = imgUrl?.toUri()?.buildUpon()?.build()
            Glide.with(GlobalApplication.appContext!!).load(imgUri)
                    .fitCenter()
                    .centerCrop()
                    .into(binding.bookThumbnail)
        }

        handleDelete()
        handleUpdate()
        handlePickDate()

        return binding.root
    }

    fun changeRead(imageView: ImageView) {
        val cursor = dbHelper.getRead(book.id)
        var read = book.read
        while (cursor.moveToNext()) {
            read = cursor.getInt(0)
        }
        if (read == 0) {
            imageView.setImageResource(R.drawable.unread_icon)
        } else {
            imageView.setImageResource(R.drawable.read_icon)
        }
    }
    fun changeFav(imageView: ImageView) {
        val cursor = dbHelper.getFavorite(book.id)
        var favorite = book.favorite
        while (cursor.moveToNext()) {
                favorite = cursor.getInt(0)
        }
        if (favorite == 0) {
            imageView.setImageResource(R.drawable.unfilledfavorite)
        } else {
            imageView.setImageResource(R.drawable.favorite_fill)
        }
    }

    fun addToFavourite(imageView: ImageView) {

        try {

            imageView.setOnClickListener {
                val cursor = dbHelper.getFavorite(book.id)
                var favorite = 0

                while (cursor.moveToNext()) {
                       favorite = cursor.getInt(0)
                }
                if (favorite.equals(0)) {
                    dbHelper.updateFavorite(book.id, 1)
                    imageView.setImageResource(R.drawable.favorite_fill)
                } else {
                    dbHelper.updateFavorite(book.id, 0)
                    imageView.setImageResource(R.drawable.unfilledfavorite)
                }

            }

        } catch (e: Exception) {
            Log.i("addToFavourite", e.toString())

        }
    }
    fun addToRead(imageView: ImageView) {

        try {

            imageView.setOnClickListener {
                val cursor = dbHelper.getRead(book.id)
                var read = 0

                while (cursor.moveToNext()) {
                    read = cursor.getInt(0)
                }

                if (read.equals(0)) {
                    dbHelper.updateRead(book.id, 1)
                    imageView.setImageResource(R.drawable.read_icon)
                } else {
                    var builder = AlertDialog.Builder(activity)
                    builder.setTitle("Do you want to read this book again")
                    builder.setMessage(book.title)
                    //builder.setCancelable(true)
                    builder.setPositiveButton("YES"){dialog, which->
                        dbHelper.updateRead(book.id, 0)
                        imageView.setImageResource(R.drawable.unread_icon)
                        Toast.makeText(activity,"${book.title} is unread",Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("No"){dialog,which ->
                        Toast.makeText(activity,"nothing has changed",Toast.LENGTH_SHORT).show()
                    }
                    val dialog: AlertDialog = builder.create()

                    dialog.show()

                }

            }

        } catch (e: Exception) {
            Log.i("addToRead", e.toString())

        }
    }

    fun handleDelete() {
        binding.deleteBtn.setOnClickListener {
            var builder = AlertDialog.Builder(activity)
            builder.setTitle("Do you want to delete this book")
            builder.setMessage(book.title)
            builder.setPositiveButton("YES"){dialog, which->
                dbHelper.deleteData(book.id)
                supportFragmentManager.popBackStack()
                showToast("${book.title} was removed")
            }
            builder.setNegativeButton("No"){dialog,which ->
                Toast.makeText(activity,"nothing has changed",Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()

            dialog.show()

        }
    }

    private fun handleUpdate() {
        binding.updateBtn.setOnClickListener {
            dbHelper.updateData(
                    book.id,
                    rentalDateString,
                    returnDateString,
                    calcRemainingDays()
            )
            showToast("Data zostałą zmieniona")
            supportFragmentManager.popBackStack()
        }
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleReturnDate(
            c: Calendar,
            year: Int,
            month: Int,
            day: Int,
            sdf: SimpleDateFormat
    ) {
        binding.returnDateBtn.setOnClickListener {
            activity?.let { it1 ->
              var dpd =  DatePickerDialog(
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
        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val localDate: LocalDateTime = LocalDateTime.now()
        val sysDate: LocalDate =
                LocalDate.of(localDate.year, localDate.monthValue, localDate.dayOfMonth)

        val valDate: LocalDate =
                LocalDate.parse(returnDateString, formater)
        return ChronoUnit.DAYS.between(sysDate, valDate).toInt()

    }

    fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }

}