package com.laurindo.lembreteapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btnLogout: Button
    private lateinit var textViewSaudacao: TextView
    private lateinit var editTextLembrete: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnDeletar: Button
    private lateinit var textViewLembrete: TextView

    private val PREFS_NAME = "meus_lembretes"
    private val CHAVE_LEMBRETES = "lembretes_salvos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        textViewSaudacao = findViewById(R.id.textViewSaudacao)
        editTextLembrete = findViewById(R.id.editTextLembrete)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnDeletar = findViewById(R.id.btnDeletar)
        textViewLembrete = findViewById(R.id.textViewLembrete)
        btnLogout = findViewById(R.id.btnLogout)

        val user = auth.currentUser
        if (user != null) {
            textViewSaudacao.text = "Olá, ${user.email}"
        } else {
            textViewSaudacao.text = "Olá"
        }

        carregarLembretes()

        btnSalvar.setOnClickListener {
            val texto = editTextLembrete.text.toString().trim()
            if (texto.isNotEmpty()) {
                val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val lembretesAtualizados = prefs.getStringSet(CHAVE_LEMBRETES, setOf())?.toMutableSet() ?: mutableSetOf()
                lembretesAtualizados.add(texto)

                prefs.edit().putStringSet(CHAVE_LEMBRETES, lembretesAtualizados).apply()

                textViewLembrete.text = lembretesAtualizados.joinToString(separator = "\n")
                editTextLembrete.text.clear()
                Toast.makeText(this, "Lembrete salvo!", Toast.LENGTH_SHORT).show()
            }
        }

        btnDeletar.setOnClickListener {
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().remove(CHAVE_LEMBRETES).apply()
            textViewLembrete.text = "Nenhum lembrete salvo"
            Toast.makeText(this, "Lembretes deletados", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            val sharedPref = getSharedPreferences("lembrete_prefs", Context.MODE_PRIVATE)
            sharedPref.edit().remove("token").apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun carregarLembretes() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lembretesSalvos = prefs.getStringSet(CHAVE_LEMBRETES, setOf()) ?: setOf()

        textViewLembrete.text = if (lembretesSalvos.isNotEmpty())
            lembretesSalvos.joinToString(separator = "\n")
        else
            "Nenhum lembrete salvo"
    }
}
