package com.example.projektzd.fragments.databaseFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration

import com.example.projektzd.R
import com.example.projektzd.adapters.*
import com.example.projektzd.adapters.adapterDatabase.GetEntities
import com.example.projektzd.adapters.adapterDatabase.RecyclerAdapterDatabase
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentListBinding

class ListFragment(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper
) : Fragment() {

    lateinit var recyclerAdapterDatabase: RecyclerAdapterDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        val entities = GetEntities(dbHelper)

        recyclerAdapterDatabase =
            RecyclerAdapterDatabase(
                supportFragmentManager,
                dbHelper,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })

        binding.databaseList.adapter = recyclerAdapterDatabase
        binding.databaseList.addItemDecoration(
            DividerItemDecoration(
                container?.context,
                DividerItemDecoration.VERTICAL
            )
        )

        entities.getEntities().observe(this, Observer {
            it?.let {
                recyclerAdapterDatabase.setBooks(it)
            }
        })

        return binding.root
    }
}
