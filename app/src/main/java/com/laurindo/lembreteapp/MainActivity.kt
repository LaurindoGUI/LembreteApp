package com.laurindo.lembreteapp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextLembrete: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnDeletar: Button
    private lateinit var textViewLembrete: TextView
    private lateinit var textViewSaudacao: TextView

    private val PREFS_NAME = "meus_lembretes"
    private val CHAVE_LEMBRETE = "lembrete_salvo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextLembrete = findViewById(R.id.editTextLembrete)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnDeletar = findViewById(R.id.btnDeletar)
        textViewLembrete = findViewById(R.id.textViewLembrete)
        textViewSaudacao = findViewById(R.id.textViewSaudacao)

        val nomeUsuario = intent.getStringExtra("NOME_USUARIO")
        if (!nomeUsuario.isNullOrEmpty()) {
            textViewSaudacao.text = "Olá, $nomeUsuario"
        }

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lembretesSalvos = prefs.getString(CHAVE_LEMBRETE, "") ?: ""
        textViewLembrete.text = if (lembretesSalvos.isNotEmpty()) lembretesSalvos else "Nenhum lembrete salvo"

        btnSalvar.setOnClickListener {
            val textoNovo = editTextLembrete.text.toString()
            if (textoNovo.isNotEmpty()) {
                val lembretesAntigos = prefs.getString(CHAVE_LEMBRETE, "") ?: ""
                val todosLembretes = if (lembretesAntigos.isNotEmpty()) {
                    "$lembretesAntigos\n$textoNovo"
                } else {
                    textoNovo
                }
                prefs.edit().putString(CHAVE_LEMBRETE, todosLembretes).apply()
                textViewLembrete.text = todosLembretes
                editTextLembrete.text.clear()
            }
        }

        btnDeletar.setOnClickListener {
            prefs.edit().remove(CHAVE_LEMBRETE).apply()
            textViewLembrete.text = "Nenhum lembrete salvo"
        }
    }
}
