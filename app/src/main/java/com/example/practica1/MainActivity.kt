package com.example.practica1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    /*
    * Datos:
    * ECG
    * D = 10
    * Paleta = #4A235A (primario), 	#F5B041 ( Acento),	#BC94CC (Primario oscuro)
    * */
    //Colores

    private lateinit var tvMensaje: TextView
    private lateinit var etxtNombre: EditText
    private lateinit var etxtMatricula: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnLimpiar: Button
    private lateinit var pbCarga: ProgressBar
    private lateinit var btnTema:Button
    private var oscuroECG = false
    private var contadorAsistentes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvMensaje = findViewById(R.id.tvMensaje)
        etxtNombre = findViewById(R.id.etxtNombre)
        etxtMatricula = findViewById(R.id.etxtMatricula)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        pbCarga = findViewById(R.id.pbCarga)

        btnRegistrar.setOnClickListener {
            revisar()
        }
        btnLimpiar.setOnClickListener { limpiar() }

        btnTema = findViewById(R.id.btnTema)

        btnTema.setOnClickListener {
            cambiarTema()
        }
    }
    fun revisar(){
        val nombreECG = etxtNombre.text.toString().trim()
        val matriculaECG = etxtMatricula.text.toString().trim()
        if (nombreECG.isEmpty()){
            Toast.makeText(this,"Escribe tu nombre", Toast.LENGTH_LONG).show()
            return
        }
        else if(nombreECG.length < 3){
            Toast.makeText(this,"El nombre es demasiado corto", Toast.LENGTH_LONG).show()
            return
        }
        else if(matriculaECG.isEmpty()){
            Toast.makeText(this,"Escribe tu matricula", Toast.LENGTH_LONG).show()
            return
        }
        else if (matriculaECG.length != 7){
            Toast.makeText(this,"La matrícula debe tener 7 dígitos", Toast.LENGTH_LONG).show()
            return
        }

        contadorAsistentes++

        pbCarga.visibility = View.VISIBLE
        tvMensaje.text = ""
        btnRegistrar.isEnabled = false
        btnLimpiar.isEnabled = false


        Handler(Looper.getMainLooper()).postDelayed({
            pbCarga.visibility = View.GONE
            tvMensaje.text = "Registrado: $nombreECG ($matriculaECG)\n Asistente número $contadorAsistentes"
            btnRegistrar.isEnabled = true
            btnLimpiar.isEnabled = true
            btnRegistrar.text = "Registrar otro"
        },1500)
    }
    fun limpiar(){
        etxtNombre.setText("")
        etxtMatricula.setText("")
        pbCarga.visibility = View.GONE
        tvMensaje.text = "Escribe tus datos para registrarte"
        btnRegistrar.text = "Registrar"
        btnRegistrar.isEnabled = true
        btnLimpiar.isEnabled = true
        etxtNombre.requestFocus()
    }
    private fun cambiarTema(){
        oscuroECG = !oscuroECG
        if (oscuroECG) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            btnTema.text = getString(R.string.btn_tema_claroECG)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            btnTema.text = getString(R.string.btn_tema_oscuroECG)
        }
    }
}