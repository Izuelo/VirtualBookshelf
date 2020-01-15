package com.example.projektzd.adapters.adapterDatabase

import androidx.lifecycle.MutableLiveData
import com.example.projektzd.database.Book
import com.example.projektzd.database.DatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GetEntities(private val dbHelper: DatabaseHelper) {

    private val entitiesList = MutableLiveData<List<Book>>()

    var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getDatabaseResponse()
    }

    private fun getDatabaseResponse() {
        coroutineScope.launch {
            val res = dbHelper.allData
            val tempList: MutableList<Book> = mutableListOf()
            while (res.moveToNext()) {
                val book = Book(
                    id = res.getString(0),
                    title = res.getString(1),
                    rentalDate = res.getString(2),
                    returnDate = res.getString(3),
                    remainingDays = res.getInt(4),
                    pageCount = res.getInt(5),
                    thumbnail = res.getString(6),
                    favorite = res.getInt(7)
                )
                tempList.add(book)
            }
            entitiesList.value = tempList
        }
    }

    fun getEntities(): MutableLiveData<List<Book>> {
        return entitiesList
    }
}