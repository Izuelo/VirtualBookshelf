package com.example.projektzd.adapters.adapterSearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projektzd.R
import com.example.projektzd.adapters.RecyclerViewClickListener
import com.example.projektzd.api.ItemsProperty
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.fragments.searchFragments.BookFragment
import java.lang.ref.WeakReference


class RecyclerAdapterSearch(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapterSearch.ViewHolderSearch>() {

    private val books: MutableList<ItemsProperty> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSearch {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderSearch(
            layoutInflater.inflate(R.layout.item_book_layout, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holderSearch: ViewHolderSearch, position: Int) {
        holderSearch.bindModel(books[position])
    }

    fun setBooks(data: List<ItemsProperty>) {
        books.addAll(data)
        notifyDataSetChanged()
    }

    fun getBook(position: Int): ItemsProperty {
        return books[position]
    }

    inner class ViewHolderSearch(
        itemView: View,
        listener: RecyclerViewClickListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        private val bookThumbnail: ImageView = itemView.findViewById(R.id.bookThumbnail)
        private val mListener: WeakReference<RecyclerViewClickListener> = WeakReference(listener)

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
                BookFragment(
                    itemsProperty,
                    dbHelper
                )
            ).addToBackStack("BookFragment").commit()
        }
    }
}
