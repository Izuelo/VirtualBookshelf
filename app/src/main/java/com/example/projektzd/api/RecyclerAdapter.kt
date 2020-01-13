package com.example.projektzd.api

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projektzd.R
import com.example.projektzd.fragments.BookFragment
import java.lang.ref.WeakReference


class RecyclerAdapter(
    supportFragmentManager: FragmentManager,
    listener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val movies: MutableList<ItemsProperty> = mutableListOf()
    private val listener = listener
    private val supportFragmentManager = supportFragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(R.layout.item_book_layout, parent, false),
            listener,
            supportFragmentManager
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(movies[position])
    }

    fun setBooks(data: List<ItemsProperty>) {
        movies.addAll(data)
        notifyDataSetChanged()
    }

    fun getBook(position: Int): ItemsProperty {
        return movies[position]
    }

    inner class ViewHolder(
        itemView: View,
        listener: RecyclerViewClickListener,
        supportFragmentManager: FragmentManager
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookThumbnail: ImageView = itemView.findViewById(R.id.bookThumbnail)
        val mListener: WeakReference<RecyclerViewClickListener> = WeakReference(listener)

        init {
            itemView.setOnClickListener(this)
        }

        fun bindModel(book: ItemsProperty) {
            bookTitle.text = book.volumeInfo.title
            val imgUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")

            imgUrl?.let {
                val imgUri = imgUrl.toUri().buildUpon()?.build()
                Glide.with(itemView.context).load(imgUri)
                    .fitCenter()
                    .centerCrop()
                    .into(bookThumbnail)
            }
        }

        override fun onClick(v: View?) {
            mListener.get()?.onClick(v!!, adapterPosition)
            val itemsProperty: ItemsProperty = getBook(adapterPosition)
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                BookFragment(itemsProperty)
            ).addToBackStack("BookFragment").commit()
        }
    }
}

interface RecyclerViewClickListener {

    fun onClick(view: View, position: Int)
}
