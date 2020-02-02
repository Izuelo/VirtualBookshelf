package com.example.projektzd.fragments.adapters

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
import com.example.projektzd.database.BookEntity
import com.example.projektzd.fragments.libraryBookFragment.LibraryBookFragment
import java.lang.ref.WeakReference
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class RecyclerAdapterDatabase(
    private val supportFragmentManager: FragmentManager,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerAdapterDatabase.ViewHolderDatabase>() {

    val entities: MutableList<BookEntity> = mutableListOf()

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

    fun setBooks(data: List<BookEntity>) {
        entities.clear()
        entities.addAll(data)
        notifyDataSetChanged()
    }

    fun getBook(position: Int): BookEntity {
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

        fun bindModel(book: BookEntity) {
            bookTitle.text = book.title
            rentalDate.text = book.rentalDate
            returnDate.text = book.returnDate
            daysLeft.text = calcRemainingDays(book.returnDate).toString()
            author.text = book.authors

            if (book.favorite == 0)
                favoriteBook.setImageResource(R.drawable.unfilledfavorite)
            else
                favoriteBook.setImageResource(R.drawable.favorite_fill)

            if (book.read == 0)
                readBook.setImageResource(R.drawable.unread_icon)
            else
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
            val book: BookEntity = getBook(adapterPosition)
            supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_out_bottom,
                R.anim.slide_in_bottom,
                R.anim.slide_out_top
            ).replace(
                R.id.fragment_container,
                LibraryBookFragment(
                    book,
                    supportFragmentManager
                )
            ).addToBackStack("ApiBookFragment").commit()
        }
    }
}

private fun calcRemainingDays(returnDateString: String): Int {
    val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val localDate: LocalDateTime = LocalDateTime.now()
    val sysDate: LocalDate =
        LocalDate.of(localDate.year, localDate.monthValue, localDate.dayOfMonth)

    val valDate: LocalDate =
        LocalDate.parse(returnDateString, formater)
    return ChronoUnit.DAYS.between(sysDate, valDate).toInt()
}
