package com.abelfavian.perpus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdminAdapter(
    private val bookList: List<Pair<String, Book>>,
    private val onDelete: (String) -> Unit,
    private val onEdit: (String, Book) -> Unit
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleTextView)
        val authorText: TextView = itemView.findViewById(R.id.authorTextView)
        val bookImage: ImageView = itemView.findViewById(R.id.bookImageView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val editButton: Button = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_buku, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val (bookId, book) = bookList[position]

        holder.titleText.text = book.title
        holder.authorText.text = book.author
        Glide.with(holder.itemView.context).load(book.imageUrl).into(holder.bookImage)

        holder.deleteButton.setOnClickListener {
            onDelete(bookId)
        }

        holder.editButton.setOnClickListener {
            onEdit(bookId, book)
        }
    }

    override fun getItemCount() = bookList.size
}
