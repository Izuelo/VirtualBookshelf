package com.example.projektzd.adapters.adapterSearch

import androidx.lifecycle.MutableLiveData
import com.example.projektzd.api.BooksApi
import com.example.projektzd.api.BooksApiFilter
import com.example.projektzd.api.ItemsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GetResponse {
    var booksList = MutableLiveData<List<ItemsProperty>>()
    var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getApiResponse()
    }

    private fun getApiResponse() {
        coroutineScope.launch {
            val getPropertiesDeferred =
                BooksApi.booksApi.getProperties(BooksApiFilter.SHOW_ITEMS.value)
            val listResult = getPropertiesDeferred.await().items
            booksList.value = listResult

        }
    }

    fun getBooks(): MutableLiveData<List<ItemsProperty>> {
        return booksList
    }
}