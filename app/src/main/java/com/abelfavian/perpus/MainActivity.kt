package com.abelfavian.perpus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var edukasitb: ImageView
    private lateinit var religitb: ImageView
    private lateinit var noveltb: ImageView
    private lateinit var komiktb: ImageView
    private lateinit var profilbt: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        edukasitb = findViewById(R.id.edukasibt)

        edukasitb.setOnClickListener {
            val intent = Intent(this, edukasi::class.java)
            startActivity(intent)
        }
        profilbt = findViewById(R.id.profil)

        profilbt.setOnClickListener {
            val intent = Intent(this, profil::class.java)
            startActivity(intent)
        }
        komiktb = findViewById(R.id.komikbt)

        komiktb.setOnClickListener {
            val intent = Intent(this, komik::class.java)
            startActivity(intent)
        }
        noveltb = findViewById(R.id.novel)

        noveltb.setOnClickListener {
            val intent = Intent(this, novel::class.java)
            startActivity(intent)
        }
        religitb = findViewById(R.id.religibt)

        religitb.setOnClickListener {
            val intent = Intent(this, religi::class.java)
            startActivity(intent)
        }
    }
}