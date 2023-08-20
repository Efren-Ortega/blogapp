package com.example.blog_app

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class Register : AppCompatActivity() {
    private lateinit var et_nombre : EditText;
    private lateinit var et_correo :EditText;
    private lateinit var et_tel : EditText;
    private lateinit var et_pass : EditText;
    private lateinit var btn : Button;
    private lateinit var btnConfirm : Button;
    private lateinit var et_confirm_pass : EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        et_pass = findViewById<EditText>(R.id.et_pass)
        var et_pass_confirm = findViewById<EditText>(R.id.et_pass_confirm)
        var showPasswordButton = findViewById(R.id.see_pass) as ImageButton

        var showPassButton = findViewById(R.id.see_pass_confirm) as ImageButton;

        showPasswordButton.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> et_pass.setInputType(InputType.TYPE_CLASS_TEXT)
                MotionEvent.ACTION_UP -> et_pass.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
            true
        })

        showPassButton.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> et_pass_confirm.setInputType(InputType.TYPE_CLASS_TEXT)
                MotionEvent.ACTION_UP -> et_pass_confirm.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
            true
        })

        et_nombre = findViewById<EditText>(R.id.et_nombre)
        et_correo = findViewById<EditText>(R.id.et_correo)
        et_confirm_pass = findViewById<EditText>(R.id.et_pass_confirm)

        btn = findViewById(R.id.btn_register) as Button



        btn.setOnClickListener {
            view -> this.registrar(view)
        }

        var tv_login = findViewById<TextView>(R.id.tv_login);
        tv_login.setOnClickListener{
            view -> this.irLogiin(view)
        }
    }

    fun irLogiin(view : View){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    fun irHome(){
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }


    fun registrar(view : View){

        if(et_nombre.length() == 0 || et_correo.length() == 0 || et_pass.length() < 8 || et_confirm_pass.length() == 0){
            if(et_nombre.length() == 0 ){
                Snackbar.make(view, "Ingrese su Nombre", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else if(et_correo.length() == 0 ){
                Snackbar.make(view, "Ingrese su Correo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else if(et_pass.length() < 8 ){
                Snackbar.make(view, "Ingrese su Contraseña de 8 caracteres", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else if(et_confirm_pass.length() == 0){
                Snackbar.make(view, "Confirme su Contraseña", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }else if(et_confirm_pass.equals(et_pass.getText())){
                Snackbar.make(view, "Su Contraseña No Coicide", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            return
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.74:8000/api/register"
        val sharedPreferences = this?.getSharedPreferences("Token", MODE_PRIVATE)

        val jsonObject = JSONObject()
        jsonObject.put("email", et_correo.text.toString())
        jsonObject.put("password", et_pass.text.toString())
        jsonObject.put("name", et_nombre.text.toString())
        jsonObject.put("password_confirmation", et_confirm_pass.text.toString())


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
                    Snackbar.make(view, "Correo ya Existe", Snackbar.LENGTH_LONG)
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





        /*val StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String?> { response ->
                val jsonObject = JSONObject(response)
                val token = jsonObject.getString("token")
                println(response)

                val editor = sharedPreferences?.edit()
                editor?.putString("token", token)
                editor?.apply()
                println("Token almacenado " +  sharedPreferences?.getString("token", ""))

                Snackbar.make(view, "Usuario Registrado", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

                this.irHome()
            },
            Response.ErrorListener {error->
                println("\n\n\n" + error)
                Snackbar.make(view, "Se produjo un Error intentelo más Tarde", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        ) {
            /*Establecer encabezados
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["authorization"] = "Barear $token" // Agregar un header estándar
                headers["Content-Type"] = "application/json"
                return headers
            }*/
        }

        queue.add(StringRequest)*/

    }

}