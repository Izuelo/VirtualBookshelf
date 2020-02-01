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
import com.example.projektzd.database.ApiBookEntity
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.fragments.bookFragments.BookFragment
import java.lang.ref.WeakReference

class RecyclerAdapterSearch(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapterSearch.ViewHolderSearch>() {

    private val books: MutableList<ApiBookEntity> = mutableListOf()

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

    fun clear() {
        books.clear()
        notifyDataSetChanged()
    }

    fun setBooks(data: List<ApiBookEntity>) {
        books.addAll(data)
        notifyDataSetChanged()
    }

    fun getBook(position: Int): ApiBookEntity {
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
        private val author: TextView = itemView.findViewById(R.id.author)

        init {
            itemView.setOnClickListener(this)
        }

        fun bindModel(book: ApiBookEntity) {
            bookTitle.text = book.title
            if (book.authors!!.isNotEmpty())
                author.text = book.authors.toString()

            val imgUrl = book.thumbnail?.replace("http://", "https://")

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
            val itemsProperty: ApiBookEntity = getBook(adapterPosition)
            supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_out_bottom,
                R.anim.slide_in_bottom,
                R.anim.slide_out_top
            ).replace(
                R.id.fragment_container,
                BookFragment(
                    itemsProperty,
                    supportFragmentManager
                )
            ).addToBackStack("BookFragment").commit()
        }
    }
}
