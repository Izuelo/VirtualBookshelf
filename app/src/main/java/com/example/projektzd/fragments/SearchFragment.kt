package com.example.projektzd.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.projektzd.GetResponse

import com.example.projektzd.R
import com.example.projektzd.api.RecyclerAdapter

class SearchFragment : Fragment() {
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val binding: Fragment
//
//                val response = GetResponse()
//
//        recyclerAdapter = RecyclerAdapter()
//        findViewById<RecyclerView>(R.id.books_list).adapter = recyclerAdapter
//        findViewById<RecyclerView>(R.id.books_list).addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//
//        response.getBooks().observe(this, Observer {
//            it?.let {
//                recyclerAdapter.setBooks(it)
//            }
//        })

        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}
