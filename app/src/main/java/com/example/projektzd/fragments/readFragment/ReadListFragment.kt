package com.example.projektzd.fragments.readFragment

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
import com.example.projektzd.fragments.adapters.RecyclerViewClickListener
import com.example.projektzd.fragments.adapters.RecyclerAdapterDatabase
import com.example.projektzd.database.BookDatabase
import com.example.projektzd.databinding.FragmentListCompletedBinding

//TODO: change to mvvm and add Room
class ReadListFragment(
    private val supportFragmentManager: FragmentManager
) : Fragment() {

    private lateinit var recyclerAdapterDatabase: RecyclerAdapterDatabase
    lateinit var binding: FragmentListCompletedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_completed, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = ReadFragmentViewModelFactory(dataSource)
        val readFragmentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ReadFragmentViewModel::class.java)

        recyclerAdapterDatabase =
            RecyclerAdapterDatabase(
                supportFragmentManager,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })

        binding.fragmentListViewModel = readFragmentViewModel
        binding.completedList.adapter = recyclerAdapterDatabase

        readFragmentViewModel.mutableBooksList.observe(this, Observer {
            it.let {
                recyclerAdapterDatabase.setBooks(it)
            }
        })

        readFragmentViewModel.modifiedBooksList.observe(this, Observer {
            it.let {
                recyclerAdapterDatabase.setBooks(it)
            }
        })

        return binding.root
    }

}
