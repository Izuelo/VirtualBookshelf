package com.example.projektzd.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.projektzd.GetResponse

import com.example.projektzd.R
import com.example.projektzd.api.RecyclerAdapter
import com.example.projektzd.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val response = GetResponse()

        recyclerAdapter = RecyclerAdapter()


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
