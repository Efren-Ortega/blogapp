package com.example.blog_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var btnOut = findViewById<ImageButton>(R.id.btnOut);

        btnOut.setOnClickListener{
            view -> salir(view)
        }
    }

    fun salir(view : View){

        val sharedPreferences = this?.getSharedPreferences("Token", MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.remove("token")
        editor?.apply()

        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}