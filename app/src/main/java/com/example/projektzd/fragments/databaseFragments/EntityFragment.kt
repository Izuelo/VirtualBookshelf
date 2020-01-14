package com.example.projektzd.fragments.databaseFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.projektzd.GlobalApplication
import com.example.projektzd.R
import com.example.projektzd.database.Book
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentEntityBinding

class EntityFragment(private val book: Book, private val dbHelper: DatabaseHelper) :
    Fragment() {

    lateinit var binding: FragmentEntityBinding
    var rentalDateString: String = " "
    var returnDateString: String = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_entity, container, false)

        binding.bookTitle.text = book.title
        binding.pageCount.text = book.pageCount.toString()
        binding.rentalDateTxt.text = book.rentalDate
        binding.returnDateTxt.text = book.returnDate

        val imgUrl = book.thumbnail.replace("http://", "https://")
        imgUrl.let {
            val imgUri = imgUrl.toUri().buildUpon()?.build()
            Glide.with(GlobalApplication.appContext!!).load(imgUri)
                .fitCenter()
                .centerCrop()
                .into(binding.bookThumbnail)
        }

        return binding.root
    }
}