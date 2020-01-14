package com.example.projektzd.adapters.adapterDatabase

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
import com.example.projektzd.database.Book
import com.example.projektzd.database.DatabaseHelper
import com.example.projektzd.fragments.databaseFragments.EntityFragment
import java.lang.ref.WeakReference


class RecyclerAdapterDatabase(
    private val supportFragmentManager: FragmentManager,
    private val dbHelper: DatabaseHelper,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapterDatabase.ViewHolderDatabase>() {

    private val entities: MutableList<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatabase {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderDatabase(
            layoutInflater.inflate(R.layout.item_entity_layout, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int {
        return entities.size
    }

    override fun onBindViewHolder(holderSearch: ViewHolderDatabase, position: Int) {
        holderSearch.bindModel(entities[position])
    }

    fun setBooks(data: List<Book>) {
        entities.addAll(data)
        notifyDataSetChanged()
    }

    fun getBook(position: Int): Book {
        return entities[position]
    }

    inner class ViewHolderDatabase(
        itemView: View,
        listener: RecyclerViewClickListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        private val rentalDate: TextView = itemView.findViewById(R.id.rentalDate)
        private val returnDate: TextView = itemView.findViewById(R.id.returnDate)
        private val daysLeft: TextView = itemView.findViewById(R.id.daysLeft)
        private val bookThumbnail: ImageView = itemView.findViewById(R.id.bookThumbnail)
        private val mListener: WeakReference<RecyclerViewClickListener> = WeakReference(listener)

        init {
            itemView.setOnClickListener(this)
        }

        fun bindModel(book: Book) {
            bookTitle.text = book.title
            rentalDate.text = book.rentalDate
            returnDate.text = book.returnDate
            daysLeft.text = book.remainingDays.toString()
            val imgUrl = book.thumbnail?.replace("http://", "https://")

            imgUrl.let {
                val imgUri = imgUrl?.toUri()?.buildUpon()?.build()
                Glide.with(itemView.context).load(imgUri)
                    .fitCenter()
                    .centerCrop()
                    .into(bookThumbnail)
            }
        }

        override fun onClick(v: View?) {
            mListener.get()?.onClick(v!!, adapterPosition)
            val book: Book = getBook(adapterPosition)
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                EntityFragment(
                    book,
                    supportFragmentManager,
                    dbHelper
                )
            ).addToBackStack("BookFragment").commit()
        }
    }
}
