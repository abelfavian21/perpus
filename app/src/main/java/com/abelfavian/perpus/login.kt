package com.abelfavian.perpus

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    lateinit var editPassword: EditText
    lateinit var editEmail: EditText
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button
    lateinit var progresDialog: ProgressDialog

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private lateinit var arahdaftar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        editEmail = findViewById(R.id.TextEmail)
        editPassword = findViewById(R.id.TextPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.btregister)

        progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Logging")
        progresDialog.setMessage("Mohon tunggu...")

        btnLogin.setOnClickListener{
            if (editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                prosesLogin()

            }else{
                Toast.makeText(this, "Silahkan isi dulu semua data", LENGTH_SHORT).show()
            }
        }
        btnLogin.setOnLongClickListener {
            val intent = Intent(this, home_admin::class.java)
            startActivity(intent)
            finish()
            true // Return true to indicate the long click is handled
        }
        btnRegister.setOnClickListener{
            startActivity(Intent(this, daftar::class.java))
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        arahdaftar = findViewById(R.id.btregister)

        arahdaftar.setOnClickListener {
            val intent = Intent(this, daftar::class.java)
            startActivity(intent)
        }
    }

    private fun prosesLogin(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        progresDialog.show()


        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                    startActivity(Intent(this, MainActivity::class.java))
                }
            .addOnFailureListener { error ->
                Toast.makeText(this,error.localizedMessage, LENGTH_SHORT) .show()

            }
            .addOnCompleteListener {
               progresDialog.dismiss()
            }
            }


    }
