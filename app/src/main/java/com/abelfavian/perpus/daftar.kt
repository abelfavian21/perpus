package com.abelfavian.perpus

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.checkerframework.checker.interning.qual.FindDistinct

class daftar : AppCompatActivity() {

    lateinit var editFullName: EditText
    lateinit var editPassword: EditText
    lateinit var editEmail: EditText
    lateinit var editPasswordConf: EditText
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button
    lateinit var progresDialog: ProgressDialog
    private lateinit var arahlogin: Button
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, profil::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar)
        editFullName = findViewById(R.id.nama)
        editEmail = findViewById(R.id.TextEmail)
        editPassword = findViewById(R.id.TextPassword)
        editPasswordConf = findViewById(R.id.Cpassword)
        btnRegister = findViewById(R.id.daftar)

        progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Logging")
        progresDialog.setMessage("Mohon tunggu...")



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRegister.setOnClickListener {
            if (editFullName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()) {
                if (editPassword.text.toString() == editPasswordConf.text.toString()) {
                    processRegister()

                } else {
                    Toast.makeText(this, "kata sandi harus sama", LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Silahkan isi dulu semua data", LENGTH_SHORT).show()

            }


            arahlogin = findViewById(R.id.login)


            arahlogin.setOnClickListener {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        }

    }

    private fun processRegister() {
        val Fullname = editFullName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        progresDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val userUpdateProfil = userProfileChangeRequest {
                        displayName = Fullname
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfil)
                        .addOnCompleteListener {
                            progresDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))

                        }
                        .addOnFailureListener{error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()
                        }


                }else{
                    progresDialog.dismiss()
                }
            }
            .addOnFailureListener { error ->
              Toast.makeText(this,error.localizedMessage, LENGTH_SHORT) .show()
            }
    }
}
