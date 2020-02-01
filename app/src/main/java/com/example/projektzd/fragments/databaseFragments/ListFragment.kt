package com.example.projektzd.fragments.databaseFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.projektzd.R
import com.example.projektzd.adapters.*
import com.example.projektzd.adapters.adapterDatabase.RecyclerAdapterDatabase
import com.example.projektzd.database.BookDatabase
import com.example.projektzd.database.BookEntity
import com.example.projektzd.databinding.FragmentListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ListFragment(
    private val supportFragmentManager: FragmentManager
) : Fragment() {

    lateinit var recyclerAdapterDatabase: RecyclerAdapterDatabase
    var favoriteOrNot = true
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

        //TODO: add sorting by favourite (serduszko <3)
        //TODO: add Toasts or other form of confirmation when deleting, updating and inserting
        var listIt: MutableList<BookEntity> = mutableListOf()

        listFragmentViewModel.mutableBooksList.observe(this, Observer {
            it?.let {
                listIt.addAll(it)
                recyclerAdapterDatabase.setBooks(it)
//                it.forEach {
//                    it.remainingDays = calcRemainingDays(it.returnDate)
//                    if (it.remainingDays <= 3)
//                        Toast.makeText(
//                            activity,
//                            "Your rental period is coming to an end",
//                            Toast.LENGTH_LONG
//                        ).show()
//                }
            }
        })

        var favoriteListFragment: FloatingActionButton = binding.favorite

//        favoriteListFragment.setOnClickListener {
//            favoriteOrNot = !favoriteOrNot
//
//            if (favoriteOrNot) {
//                recyclerAdapterDatabase.setBooks(listIt)
//            } else {
//                var booksList: MutableList<Book> = mutableListOf()
//
//                listIt.forEach {
//                    if (it.favorite == 1)
//                        booksList.add(it)
//                }
//                recyclerAdapterDatabase.setBooks(booksList)
//            }
//
//        }
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
