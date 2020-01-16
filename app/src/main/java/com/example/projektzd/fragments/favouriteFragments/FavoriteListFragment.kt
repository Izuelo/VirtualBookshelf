package com.example.projektzd.fragments.favouriteFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer

import com.example.projektzd.R
import com.example.projektzd.adapters.RecyclerViewClickListener
import com.example.projektzd.adapters.adapterDatabase.GetEntities
import com.example.projektzd.adapters.adapterDatabase.RecyclerAdapterDatabase
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentListFavoriteBinding

class FavoriteListFragment(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper

) : Fragment() {

    lateinit var recyclerAdapterDatabase: RecyclerAdapterDatabase
    lateinit var binding: FragmentListFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_favorite, container, false)

        val entities = GetEntities(dbHelper)
        entities.getDatabaseResponseFav()

        recyclerAdapterDatabase =
            RecyclerAdapterDatabase(
                supportFragmentManager,
                dbHelper,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })

        binding.favoriteList.adapter = recyclerAdapterDatabase

        entities.getEntities().observe(this, Observer {
            it?.let {
                recyclerAdapterDatabase.setBooks(it)
            }
        })

        return binding.root
    }


}
