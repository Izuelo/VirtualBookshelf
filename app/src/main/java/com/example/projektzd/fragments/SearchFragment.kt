package com.example.projektzd.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.projektzd.GetResponse
import com.example.projektzd.GlobalApplication

import com.example.projektzd.R
import com.example.projektzd.api.RecyclerAdapter
import com.example.projektzd.api.RecyclerViewClickListener
import com.example.projektzd.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment(supportFragmentManager: FragmentManager) : Fragment() {

    lateinit var recyclerAdapter: RecyclerAdapter
    val supportFragmentManager = supportFragmentManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val response = GetResponse()

        recyclerAdapter = RecyclerAdapter(supportFragmentManager, object : RecyclerViewClickListener {
            override fun onClick(view: View, position: Int){

            }
        })

        binding.booksList.adapter = recyclerAdapter
        binding.booksList.addItemDecoration(
            DividerItemDecoration(
                container?.context,
                DividerItemDecoration.VERTICAL
            )
        )

        response.getBooks().observe(this, Observer {
            it?.let {
                recyclerAdapter.setBooks(it)
            }
        })

        return binding.root
    }
}
