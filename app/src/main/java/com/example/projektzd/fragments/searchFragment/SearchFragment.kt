package com.example.projektzd.fragments.searchFragment

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer

import com.example.projektzd.R
import com.example.projektzd.fragments.adapters.RecyclerAdapterSearch
import com.example.projektzd.fragments.adapters.RecyclerViewClickListener
import com.example.projektzd.databinding.FragmentSearchBinding
import android.app.SearchManager
import android.content.Context

import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.example.projektzd.database.BookDatabase

class SearchFragment(
    private val supportFragmentManager: FragmentManager
) : Fragment() {

    lateinit var searchFragmentViewModel: SearchFragmentViewModel
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

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

        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = SearchFragmentViewModelFactory(dataSource)
        searchFragmentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchFragmentViewModel::class.java)


        val recyclerAdapterSearch =
            RecyclerAdapterSearch(
                supportFragmentManager,
                object : RecyclerViewClickListener {
                    override fun onClick(view: View, position: Int) {

                    }
                })
        binding.fragmentSearchViewModel = searchFragmentViewModel
        binding.booksList.adapter = recyclerAdapterSearch
        binding.lifecycleOwner = this

        searchFragmentViewModel.mutableBooksList.observe(this, Observer {
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
                    searchFragmentViewModel.getApiResponse(query)
                    searchView?.clearFocus()
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
