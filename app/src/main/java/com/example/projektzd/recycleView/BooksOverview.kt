package com.example.projektzd.recycleView

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projektzd.R
import com.example.projektzd.databinding.FragmentMainBinding
import com.example.projektzd.databinding.ActivityMainBinding

import android.app.Activity
import android.util.Log


class BooksOverview : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("XDXDXD", "XDXDXD")
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
//        val binding = DataBindingUtil.setContentView(Activity(), R.layout.fragment_main)
        val booksOverview= ViewModelProviders.of(this).get(Overview::class.java)
        val adapter = FragmentAdapter()

        binding.booksOverview = booksOverview
        binding.lifecycleOwner = this

        booksOverview.booksList?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}