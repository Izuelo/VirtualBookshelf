package com.example.projektzd.fragments.databaseFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
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
import com.example.projektzd.databinding.FragmentFavoriteBinding
import com.example.projektzd.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_entity.view.*
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FavoriteFragment(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper

) : Fragment() {

    lateinit var recyclerAdapterDatabase: RecyclerAdapterDatabase
    lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        return binding.root
    }


}
