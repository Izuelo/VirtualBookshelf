package com.example.projektzd.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.projektzd.GlobalApplication
import com.example.projektzd.R
import com.example.projektzd.api.ItemsProperty
import com.example.projektzd.databinding.BookFragmentBinding
import com.example.projektzd.databinding.FragmentSearchBinding

class BookFragment(book: ItemsProperty) : Fragment() {

    val book: ItemsProperty = book

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: BookFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.book_fragment, container, false)

        binding.bookTitle.text = book.volumeInfo.title
        binding.pageCount.text = book.volumeInfo.pageCount.toString()

        val imgUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon()?.build()
            Glide.with(GlobalApplication.appContext!!).load(imgUri)
                .fitCenter()
                .centerCrop()
                .into(binding.bookThumbnail)
        }

        return binding.root
    }
}