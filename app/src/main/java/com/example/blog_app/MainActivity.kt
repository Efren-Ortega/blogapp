package com.example.blog_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = this?.getSharedPreferences("Token", MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")

        if(!token.equals("")){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }
}