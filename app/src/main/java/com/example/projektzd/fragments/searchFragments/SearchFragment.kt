package com.example.projektzd.fragments.searchFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.projektzd.adapters.adapterSearch.GetResponse

import com.example.projektzd.R
import com.example.projektzd.adapters.adapterSearch.RecyclerAdapterSearch
import com.example.projektzd.adapters.RecyclerViewClickListener
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentSearchBinding


class SearchFragment(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper
) : Fragment() {

    lateinit var recyclerAdapterSearch: RecyclerAdapterSearch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val response = GetResponse()

        recyclerAdapterSearch =
            RecyclerAdapterSearch(
                supportFragmentManager,
                dbHelper,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })

        binding.booksList.adapter = recyclerAdapterSearch
        binding.booksList.addItemDecoration(
            DividerItemDecoration(
                container?.context,
                DividerItemDecoration.VERTICAL
            )
        )

        response.getBooks().observe(this, Observer {
            it?.let {
                recyclerAdapterSearch.setBooks(it)
            }
        })

        return binding.root
    }
}
