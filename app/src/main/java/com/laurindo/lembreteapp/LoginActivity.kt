package com.laurindo.lembreteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editSobrenome: EditText
    private lateinit var btnAvancar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editNome = findViewById(R.id.editNome)
        editSobrenome = findViewById(R.id.editSobrenome)
        btnAvancar = findViewById(R.id.btnAvancar)

        btnAvancar.setOnClickListener {
            val nome = editNome.text.toString().trim()
            val sobrenome = editSobrenome.text.toString().trim()

            if (nome.isNotEmpty() && sobrenome.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NOME_USUARIO", "$nome $sobrenome")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, preencha nome e sobrenome", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
