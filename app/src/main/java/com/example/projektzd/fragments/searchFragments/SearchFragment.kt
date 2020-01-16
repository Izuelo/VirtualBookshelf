package com.example.projektzd.fragments.searchFragments

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.projektzd.adapters.adapterSearch.GetResponse

import com.example.projektzd.R
import com.example.projektzd.adapters.adapterSearch.RecyclerAdapterSearch
import com.example.projektzd.adapters.RecyclerViewClickListener
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.databinding.FragmentSearchBinding
import android.app.SearchManager
import android.content.Context
import android.util.Log

import android.view.*
import androidx.appcompat.widget.SearchView

class SearchFragment(
        private val supportFragmentManager: FragmentManager,
        private val dbHelper: DatabaseHelper
) : Fragment() {

    lateinit var recyclerAdapterSearch: RecyclerAdapterSearch
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    val response: GetResponse = GetResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSearchBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        recyclerAdapterSearch =
                RecyclerAdapterSearch(
                        supportFragmentManager,
                        dbHelper,
                        object : RecyclerViewClickListener {
                            override fun onClick(view: View, position: Int) {

                            }
                        })

        binding.booksList.adapter = recyclerAdapterSearch
        binding.lifecycleOwner = this

        response.getBooks().observe(this, Observer {
            it?.let {
                recyclerAdapterSearch.clear()
                recyclerAdapterSearch.setBooks(it)

            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchManager =
                activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    response.getApiResponse(query)
                    searchView?.clearFocus()
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
