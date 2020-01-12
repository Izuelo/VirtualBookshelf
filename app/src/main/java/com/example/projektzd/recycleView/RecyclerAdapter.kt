package com.example.projektzd.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projektzd.api.ItemsProperty
import com.example.projektzd.databinding.BookItemBinding

class FragmentAdapter :
    ListAdapter<ItemsProperty, BookViewHolder>(BooksDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }
}

class BookViewHolder private constructor(private val binding: BookItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(book: ItemsProperty) {
        binding.book = book
    }

    companion object {
        fun from(parent: ViewGroup): BookViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = BookItemBinding.inflate(layoutInflater, parent, false)
            return BookViewHolder(view)
        }
    }
}

class BooksDiff : DiffUtil.ItemCallback<ItemsProperty>() {
    override fun areItemsTheSame(oldItem: ItemsProperty, newItem: ItemsProperty): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemsProperty, newItem: ItemsProperty): Boolean {
        return oldItem == newItem
    }
}
