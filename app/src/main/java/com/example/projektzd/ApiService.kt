package com.example.projektzd

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val BASE_URL = "https://www.googleapis.com/books/v1/"

enum class BooksApiFilter(val value: String) { SHOW_ITEMS("intitle:Harry Potter i Komnata Tajemnic") }

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface BooksApiService {
    @GET("volumes")
    fun getProperties(@Query("q") type: String):
            Deferred<VolumesProperty>
}

object BooksApi {
    val booksApi: BooksApiService by lazy { retrofit.create(BooksApiService::class.java) }
}