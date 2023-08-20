package com.example.blog_app.Modelo

import java.util.UUID

class Usuario(var correo: String, var password: String) {
    var token: String = ""

    fun login(): Pair<String, String>? {
        // Aquí colocas la lógica de autenticación
        // y obtienes el token del servidor
        // ...

        // Generar un token ficticio
        val token = generarToken()

        // Retornar el token y el correo como un par de valores (Pair)
        return Pair(token, correo)
    }

    private fun generarToken(): String {
        // Generar un token aleatorio usando UUID
        return UUID.randomUUID().toString()
    }
}
