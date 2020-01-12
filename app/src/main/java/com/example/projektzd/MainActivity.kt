package com.example.projektzd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.projektzd.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rollButton.setOnClickListener {
            binding.rollValue.text = floor(Math.random() * 6 + 1).toString()
        }

        var viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

        coroutineScope.launch {
            val getPropertiesDeferred =
                BooksApi.booksApi.getProperties(BooksApiFilter.SHOW_ITEMS.value)
            val listResult = getPropertiesDeferred.await()
            Log.i("aaabbbccc", listResult.toString())
        }

    }
}

