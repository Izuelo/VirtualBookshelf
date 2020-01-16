package com.example.projektzd.adapters.adapterDatabase

import android.util.Log
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
        entities.clear()
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
        private val favoriteBook: ImageView = itemView.findViewById(R.id.fill_heart)
        private val readBook: ImageView = itemView.findViewById(R.id.read_view)
        private val author: TextView = itemView.findViewById(R.id.author)

        init {
            itemView.setOnClickListener(this)
        }

        fun bindModel(book: Book) {
            bookTitle.text = book.title
            rentalDate.text = book.rentalDate
            returnDate.text = book.returnDate
            daysLeft.text = book.remainingDays.toString()
            author.text = book.authors
            val res = dbHelper.getFavorite(book.id)
            val cursor = dbHelper.getRead(book.id)
            var favorite = 0
            var read = 0
            while (res.moveToNext()) {
                favorite = res.getInt(0)
            }
            while (cursor.moveToNext()) {
                read = cursor.getInt(0)
            }
            if (favorite.equals(0)) {
                favoriteBook.setImageResource(R.drawable.unfilledfavorite)
            } else
                favoriteBook.setImageResource(R.drawable.favorite_fill)

            if (read.equals(0)) {
                readBook.setImageResource(R.drawable.unread_icon)
            } else
                readBook.setImageResource(R.drawable.read_icon)

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
            supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_out_bottom,
                R.anim.slide_in_bottom,
                R.anim.slide_out_top
            ).replace(
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
