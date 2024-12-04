package com.abelfavian.perpus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class profil : AppCompatActivity() {
    lateinit var textFullName: TextView
    lateinit var textEmail: TextView
    lateinit var btnLogout: Button
    lateinit var btnDeleteAccount: Button  // Deklarasi tombol Hapus Akun

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profil)

        textFullName = findViewById(R.id.full_name)
        textEmail = findViewById(R.id.email)
        btnLogout = findViewById(R.id.btn_logout)
        btnDeleteAccount = findViewById(R.id.btn_delete_account)  // Inisialisasi tombol Hapus Akun

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            textFullName.text = firebaseUser.displayName
            textEmail.text = firebaseUser.email
        } else {
            startActivity(Intent(this, login::class.java))
            finish()
        }

        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, login::class.java))
            finish()
        }

        // Fungsi untuk menghapus akun
        btnDeleteAccount.setOnClickListener {
            firebaseUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, login::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Gagal menghapus akun: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}