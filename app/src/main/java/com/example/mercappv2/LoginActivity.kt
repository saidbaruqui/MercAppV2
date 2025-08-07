package com.example.mercappv2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mercappv2.util.SessionManager


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Ajuste visual para evitar que la barra superior tape el contenido
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los campos del formulario
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etClave = findViewById<EditText>(R.id.etClave)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Acción del botón de login
        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val clave = etClave.text.toString().trim()

            // Validación básica de credenciales
            val tipoUsuario = when {
                usuario == "admin" && clave == "admin123" -> "admin"
                usuario == "empleado" && clave == "empleado123" -> "empleado"
                else -> {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val sessionManager = SessionManager(this)
            sessionManager.guardarSesion(
                idUsuario = 1, // puedes usar el real si lo tienes
                nombre = usuario,
                correo = "$usuario@email.com",
                rol = tipoUsuario
            )

            // Redirige a MainActivity con el tipo de usuario
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("tipoUsuario", tipoUsuario)
            startActivity(intent)
            finish()
        }
    }
}
