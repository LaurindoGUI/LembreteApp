package com.laurindo.lembreteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.editEmailRegister)
        editSenha = findViewById(R.id.editSenhaRegister)
        btnRegistrar = findViewById(R.id.btnCadastrarRegister)

        btnRegistrar.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val senha = editSenha.text.toString().trim()

            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Falha no cadastro: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }


        }
    }

