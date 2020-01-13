package com.example.projektzd

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getApiResponse()
    }

    fun getApiResponse() {
        coroutineScope.launch {
            try {
                val getPropertiesDeferred =
                    BooksApi.booksApi.getProperties(BooksApiFilter.SHOW_ITEMS.value)
                val listResult = getPropertiesDeferred.await().items
                booksList.value = listResult
            } catch (e: Exception) {
                Log.i("eeeXXX", e.toString())
            }
        }
    }

    fun getBooks(): MutableLiveData<List<ItemsProperty>> {
        return booksList
    }
}