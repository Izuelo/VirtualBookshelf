package com.example.projektzd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.projektzd.api.RecyclerAdapter
import com.example.projektzd.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val response = GetResponse()

        recyclerAdapter = RecyclerAdapter()
        findViewById<RecyclerView>(R.id.books_list).adapter = recyclerAdapter
        findViewById<RecyclerView>(R.id.books_list).addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        response.getBooks().observe(this, Observer {
            it?.let {
                recyclerAdapter.setBooks(it)
            }
        })
    }

}


