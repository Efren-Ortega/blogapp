package com.example.blog_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blog_app.Controlador.LoginController
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class Login : AppCompatActivity() {

    public lateinit var et_correo :EditText;
    public lateinit var et_pass : EditText;
    public lateinit var showPasswordButton : ImageButton;
    public lateinit var tv_register : TextView;
    public lateinit var btn_inciarSesion : Button;
    private lateinit var loginController : LoginController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_pass = findViewById<EditText>(R.id.et_pass)
        showPasswordButton = findViewById(R.id.see_pass) as ImageButton


        et_correo = findViewById<EditText>(R.id.et_correo)

        tv_register = findViewById<TextView>(R.id.tv_register);
        btn_inciarSesion = findViewById<Button>(R.id.btn_ingresar);

        tv_register.setOnClickListener{
            view -> this.irRegister(view)
        }

        loginController = LoginController(this)

    }

    fun irRegister(view : View){
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    fun irHome(){
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun iniciarSesion(view: View){


        if(et_correo.length() == 0 || et_pass.length() == 0 ){
            if(et_correo.length() == 0 ){
                Snackbar.make(view, "Ingrese su Correo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else if(et_pass.length() == 0 ){
                Snackbar.make(view, "Ingrese su Contraseña", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            return
        }


        val url = "http://192.168.137.217:80/distribuidora/InicioSesion.php/?email=${et_correo.text}&password=${et_pass.text}"

        val queue = Volley.newRequestQueue(this)

        val sharedPreferences = this?.getSharedPreferences("Token", MODE_PRIVATE)

        val solicitud = JsonObjectRequest(
            Request.Method.GET, url, null,
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
                    Snackbar.make(view, "Verifique Sus Campos", Snackbar.LENGTH_LONG)
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

}