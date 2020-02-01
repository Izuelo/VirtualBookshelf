package com.example.projektzd.fragments.favouriteFragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.example.projektzd.R
import com.example.projektzd.adapters.RecyclerViewClickListener
import com.example.projektzd.adapters.adapterDatabase.RecyclerAdapterDatabase
import com.example.projektzd.databinding.FragmentListCompletedBinding


//TODO: change to mvvm and add Room
class CompletedListFragment(
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

        recyclerAdapterDatabase =
            RecyclerAdapterDatabase(
                supportFragmentManager,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })

        binding.completedList.adapter = recyclerAdapterDatabase

//        entities.getEntities().observe(this, Observer {
//            it?.let {
//                recyclerAdapterDatabase.setBooks(it)
//            }
//        })

        return binding.root
    }

}
