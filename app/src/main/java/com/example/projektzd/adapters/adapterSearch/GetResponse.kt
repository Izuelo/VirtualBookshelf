package com.example.projektzd.adapters.adapterSearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projektzd.api.BooksApi
import com.example.projektzd.api.ItemsProperty
import kotlinx.coroutines.*

class GetResponse {
    var booksList = MutableLiveData<List<ItemsProperty>>()
    var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getApiResponse(search: String) {
        coroutineScope.launch {
            try {
                if (!search.isNullOrEmpty()) {
                    val getPropertiesDeferred =
                        BooksApi.booksApi.getProperties("intitle:$search")
                    val listResult = getPropertiesDeferred.await().items
                    booksList.value = listResult
                }
            } catch (e: Exception) {
                Log.i("SEARCHEX", e.toString())
            }
        }
    }

    fun getBooks(): MutableLiveData<List<ItemsProperty>> {
        return booksList
    }
}