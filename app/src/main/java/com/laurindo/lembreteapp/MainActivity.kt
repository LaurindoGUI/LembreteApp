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

    private val PREFS_NAME = "meus_lembretes"
    private val CHAVE_LEMBRETE = "lembrete_salvo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextLembrete = findViewById(R.id.editTextLembrete)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnDeletar = findViewById(R.id.btnDeletar)
        textViewLembrete = findViewById(R.id.textViewLembrete)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lembreteSalvo = prefs.getString(CHAVE_LEMBRETE, "")
        textViewLembrete.text = if (lembreteSalvo!!.isNotEmpty()) lembreteSalvo else "Nenhum lembrete salvo"

        btnSalvar.setOnClickListener {
            val texto = editTextLembrete.text.toString()
            if (texto.isNotEmpty()) {
                prefs.edit().putString(CHAVE_LEMBRETE, texto).apply()
                textViewLembrete.text = texto
                editTextLembrete.text.clear()
            }
        }

        btnDeletar.setOnClickListener {
            prefs.edit().remove(CHAVE_LEMBRETE).apply()
            textViewLembrete.text = "Nenhum lembrete salvo"
        }
    }
}