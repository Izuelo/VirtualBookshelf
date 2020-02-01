package com.example.projektzd.fragments.searchFragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projektzd.api.BooksApi
import com.example.projektzd.api.toEntity
import com.example.projektzd.database.ApiBookEntity
import com.example.projektzd.database.BookDao
import kotlinx.coroutines.*
import java.util.*

class SearchFragmentViewModel(
    val database: BookDao
) : ViewModel() {
    private var list = LinkedList<ApiBookEntity>()
    var mutableBooksList: MutableLiveData<List<ApiBookEntity>> = MutableLiveData(list)
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun getApiResponse(search: String) {
        coroutineScope.launch {
            try {
                if (search.isNotEmpty()) {
                    val getPropertiesDeferred =
                        BooksApi.booksApi.getProperties("intitle:$search")
                    val booksList = getPropertiesDeferred.await().items
                    val booksEntityList = booksList.toEntity()
                    database.insertApiBooks(*booksEntityList.toTypedArray())

                    val select = database.getSelectedBooks("%$search%")
                    list.clear()
                    list.addAll(select)
                    mutableBooksList.postValue(list)

                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("SEARCHEX", e.toString())
            }
        }
    }

}