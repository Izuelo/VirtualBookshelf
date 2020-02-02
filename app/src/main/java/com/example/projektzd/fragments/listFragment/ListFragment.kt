package com.example.projektzd.fragments.listFragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.projektzd.R
import com.example.projektzd.fragments.adapters.*
import com.example.projektzd.fragments.adapters.RecyclerAdapterDatabase
import com.example.projektzd.database.BookDatabase
import com.example.projektzd.databinding.FragmentListBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ListFragment(
    private val supportFragmentManager: FragmentManager
) : Fragment() {
    private lateinit var recyclerAdapterDatabase: RecyclerAdapterDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = ListFragmentViewModelFactory(dataSource)
        val listFragmentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ListFragmentViewModel::class.java)

        recyclerAdapterDatabase =
            RecyclerAdapterDatabase(
                supportFragmentManager,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })
        binding.fragmentListViewModel = listFragmentViewModel
        binding.databaseList.adapter = recyclerAdapterDatabase


        binding.favorite.setOnClickListener {
            listFragmentViewModel.getDatabaseFavourites()
        }

        listFragmentViewModel.mutableBooksList.observe(this, Observer {
            it.let {
                recyclerAdapterDatabase.setBooks(it)
                it.forEach { it1 ->
                    val remainingDays = calcRemainingDays(it1.returnDate)
                    if (remainingDays <= 3)
                        Toast.makeText(
                            activity,
                            "Your rental period is coming to an end",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        })

        listFragmentViewModel.modifiedBooksList.observe(this, Observer {
            it.let {
                recyclerAdapterDatabase.setBooks(it)
                listFragmentViewModel.isFavourite = false
                it.forEach { it1 ->
                    val remainingDays = calcRemainingDays(it1.returnDate)
                    if (remainingDays <= 3)
                        Toast.makeText(
                            activity,
                            "Your rental period is coming to an end",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        })
        return binding.root
    }

    private fun calcRemainingDays(returnDateString: String): Int {
        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val localDate: LocalDateTime = LocalDateTime.now()
        val sysDate: LocalDate =

            LocalDate.of(localDate.year, localDate.monthValue, localDate.dayOfMonth)

        val valDate: LocalDate =
            LocalDate.parse(returnDateString, formater)
        return ChronoUnit.DAYS.between(sysDate, valDate).toInt()
    }
}
