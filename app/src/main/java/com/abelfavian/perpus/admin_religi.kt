package com.abelfavian.perpus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class admin_religi : AppCompatActivity() {

    private lateinit var edtTitle: EditText
    private lateinit var edtAuthor: EditText
    private lateinit var edtImageUrl: EditText
    private lateinit var edtPdfUrl: EditText
    private lateinit var btnAddBook: Button
    private lateinit var btnhapus: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // Inisialisasi komponen UI
        edtTitle = findViewById(R.id.edtTitle)
        edtAuthor = findViewById(R.id.edtAuthor)
        edtImageUrl = findViewById(R.id.edtImageUrl)
        edtPdfUrl = findViewById(R.id.edtPdfUrl)
        btnAddBook = findViewById(R.id.btnAddBook)

        btnhapus = findViewById(R.id.btnhapus)

        btnhapus.setOnClickListener {
            val intent = Intent(this, admin_hapus_religi::class.java)
            startActivity(intent)
        }

        // Tombol tambah buku diklik
        btnAddBook.setOnClickListener {
            val title = edtTitle.text.toString()
            val author = edtAuthor.text.toString()
            val imageUrl = edtImageUrl.text.toString()
            val pdfUrl = edtPdfUrl.text.toString()

            // Validasi input
            if (title.isEmpty() || author.isEmpty() || imageUrl.isEmpty() || pdfUrl.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                addBookToFirebase(title, author, imageUrl, pdfUrl)
            }
        }
    }

    private fun addBookToFirebase(title: String, author: String, imageUrl: String, pdfUrl: String) {
        // Mendapatkan reference untuk menambahkan data buku
        val database = FirebaseDatabase.getInstance("https://pilketos24-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("books4")
        val bookId = database.push().key ?: return // Generate unique ID for new book

        // Membuat objek buku
        val newBook = Book(title, author, imageUrl, pdfUrl)



        // Menambahkan buku ke Firebase
        database.child(bookId).setValue(newBook)
            .addOnSuccessListener {
                // Jika berhasil
                Toast.makeText(this, "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                clearInputFields()
            }
            .addOnFailureListener {
                // Jika gagal
                Toast.makeText(this, "Gagal menambahkan buku.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearInputFields() {
        edtTitle.text.clear()
        edtAuthor.text.clear()
        edtImageUrl.text.clear()
        edtPdfUrl.text.clear()
    }
}
