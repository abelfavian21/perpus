package com.abelfavian.perpus

import android.net.Uri
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookAdapter(private val bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.coverImageView)
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        val buttonReadBook: Button = itemView.findViewById(R.id.buttonReadBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookTitle.text = book.title
        holder.bookAuthor.text = book.author  // Menampilkan nama penulis

        // Memuat gambar cover buku dengan Glide
        Glide.with(holder.itemView.context)
            .load(book.imageUrl)
            .into(holder.bookCover)

        // Set click listener untuk tombol baca buku
        holder.buttonReadBook.setOnClickListener {
            val context = holder.itemView.context
            val pdfUrl = book.pdfUrl

            if (pdfUrl.isNotEmpty()) { // Cek apakah URL PDF ada
                // URL untuk membuka PDF di browser
                val googleViewerUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url="
                val fullPdfUrl = googleViewerUrl + pdfUrl

                // Intent untuk membuka PDF di browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullPdfUrl))
                context.startActivity(intent)
            } else {
                // Jika URL PDF tidak tersedia
                Toast.makeText(context, "URL tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = bookList.size
}