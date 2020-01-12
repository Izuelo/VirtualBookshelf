package com.example.projektzd.recycleView

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.projektzd.api.BooksApi
import com.example.projektzd.api.BooksApiFilter
import com.example.projektzd.api.ItemsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Overview(
    app: Application
) : AndroidViewModel(app) {
    var booksList: LiveData<List<ItemsProperty>>? = null

    var viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getApiResponse()
    }

    fun getApiResponse() {
        coroutineScope.launch {
            Log.i("abcabc", "KEKW")
            val getPropertiesDeferred =
                BooksApi.booksApi.getProperties(BooksApiFilter.SHOW_ITEMS.value)
            val listResult = getPropertiesDeferred.await().items
            booksList = listResult!!
            Log.i("abcabc", "XDDDD")
        }
    }
}