package com.example.blog_app.Controlador

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blog_app.*
import com.example.blog_app.Modelo.Usuario
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject


class LoginController(private val loginView: Login) {

    init {
        loginView.showPasswordButton.setOnTouchListener(View.OnTouchListener({ v: View?, event: MotionEvent ->

            togglePasswordVisibility(event)

            true

        }))

        loginView.btn_inciarSesion.setOnClickListener {
                view -> iniciarSesion(view)
        }
    }

    private fun togglePasswordVisibility(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> loginView.et_pass.setInputType(InputType.TYPE_CLASS_TEXT)
            MotionEvent.ACTION_UP -> loginView.et_pass.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }
    }


    private fun iniciarSesion(view: View){

        if(loginView.et_correo.length() == 0 || loginView.et_pass.length() == 0 ){
            if(loginView.et_correo.length() == 0 ){
                Snackbar.make(view, "Ingrese su Correo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else if(loginView.et_pass.length() == 0 ){
                Snackbar.make(view, "Ingrese su Contraseña", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            return
        }


        val url = ""

        val queue = Volley.newRequestQueue(loginView)
        val sharedPreferences = loginView?.getSharedPreferences("Token", AppCompatActivity.MODE_PRIVATE)

        val jsonObject = JSONObject()
        jsonObject.put("email", loginView.et_correo.text.toString())
        jsonObject.put("password", loginView.et_pass.text.toString())

        val solicitud = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->

                try{
                    val token = response.getString("token")

                    if(token.equals("")){
                        Snackbar.make(view, "Error en la contraseña o correo", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }else{
                        val editor = sharedPreferences?.edit()
                        editor?.putString("token", token)
                        editor?.apply()
                        println(token)

                        this.irHome()
                    }

                }catch (e:Exception){
                    Snackbar.make(view, "Contraseña o Correo Incorrectos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

            },
            Response.ErrorListener { error ->
                val mensajeError = error.message
                println(mensajeError)
                Snackbar.make(view, "Se produjo un Error intentelo más Tarde", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            })

        queue.add(solicitud)
    }

    fun irHome(){
        val intent = Intent(loginView, Home::class.java)
        loginView.startActivity(intent)
    }

}
