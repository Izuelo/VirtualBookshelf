package com.example.projektzd.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

var viewModelJob = Job()
val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

fun getApiResponse() {
    coroutineScope.launch {
        val getPropertiesDeferred =
            BooksApi.booksApi.getProperties(BooksApiFilter.SHOW_ITEMS.value)
        val listResult = getPropertiesDeferred.await()
    }
}
