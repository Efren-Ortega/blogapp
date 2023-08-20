package com.example.blog_app.Controlador

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.blog_app.MainActivity
import com.example.blog_app.Register
import com.google.android.material.snackbar.Snackbar

class MainActivityController(private val activity: MainActivity) {
    private val context: Context = activity.applicationContext

    /*init {
        activity.btn_salir.setOnClickListener {
            borrarTodoEnSharedPreferences()
            val intent = Intent(context, Register::class.java)
            activity.startActivity(intent)
            activity.finish()
        }

        checkTokenInSharedPreferences()
    }

    private fun checkTokenInSharedPreferences() {
        val tokenGuardado = verificarTokenEnSharedPreferences()

        if (tokenGuardado != null) {
            activity.tv_usuario_token.setText(tokenGuardado)

            Snackbar.make(activity.btn_salir, "Inicio de sesión automático", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        } else {
            val intent = Intent(context, Register::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    private fun borrarTodoEnSharedPreferences() {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


    private fun verificarTokenEnSharedPreferences(): String? {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }*/
}