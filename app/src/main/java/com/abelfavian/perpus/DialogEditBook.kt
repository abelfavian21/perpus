package com.abelfavian.perpus

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class DialogEditBook(
    context: Context,
    private val book: Book,
    private val onSave: (Book) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_edit_book)

        val titleEditText: EditText = findViewById(R.id.titleEditText)
        val authorEditText: EditText = findViewById(R.id.authorEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val imageUrlEditText: EditText = findViewById(R.id.imageUrlEditText)

        titleEditText.setText(book.title)
        authorEditText.setText(book.author)
        descriptionEditText.setText(book.pdfUrl)
        imageUrlEditText.setText(book.imageUrl)

        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val updatedBook = book.copy(
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                pdfUrl = descriptionEditText.text.toString(),
                imageUrl = imageUrlEditText.text.toString()
            )
            onSave(updatedBook)
            dismiss()
        }

        val cancelButton: Button = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener { dismiss() }
    }
}
