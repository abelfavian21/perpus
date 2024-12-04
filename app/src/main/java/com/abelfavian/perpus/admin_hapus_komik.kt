package com.abelfavian.perpus

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class admin_hapus_komik : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var adminAdapter: AdminAdapter
    private val bookList = mutableListOf<Pair<String, Book>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_hapus)

        val recyclerView = findViewById<RecyclerView>(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        database = FirebaseDatabase.getInstance("https://pilketos24-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("books2")

        adminAdapter = AdminAdapter(
            bookList,
            onDelete = { bookId -> deleteBook(bookId) },
            onEdit = { bookId, book -> showEditDialog(bookId, book) }
        )
        recyclerView.adapter = adminAdapter

        fetchBooks()
    }

    private fun fetchBooks() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                for (bookSnapshot in snapshot.children) {
                    val bookId = bookSnapshot.key ?: continue
                    val book = bookSnapshot.getValue(Book::class.java) ?: continue
                    bookList.add(bookId to book)
                }
                adminAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@admin_hapus_komik, "Failed to fetch books", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteBook(bookId: String) {
        database.child(bookId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showEditDialog(bookId: String, book: Book) {
        val dialog = DialogEditBook(this, book) { updatedBook ->
            updateBook(bookId, updatedBook)
        }
        dialog.show()
    }

    private fun updateBook(bookId: String, updatedBook: Book) {
        database.child(bookId).setValue(updatedBook)
            .addOnSuccessListener {
                Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show()
            }
    }
}
