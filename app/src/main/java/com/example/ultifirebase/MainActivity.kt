package com.example.ultifirebase

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var titulo: EditText
    lateinit var nota: EditText
    lateinit var viewer: TextView
    lateinit var mensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titulo = findViewById(R.id.titulo)
        nota = findViewById(R.id.nota)
        viewer = findViewById(R.id.notas)
        mensaje = findViewById(R.id.messaje)
    }

    fun guardar(b: View){

        val button: Button = b as Button

        if(titulo.text.isNotBlank() && nota.text.isNotBlank()){
            val doc = hashMapOf(
                "titulo" to titulo.text.toString(),
                "nota" to nota.text.toString()
            )
            db.collection("notas").document().set(doc)
                .addOnSuccessListener { _ ->
                    mensaje.text = "NOTA GUARDADA"
                    titulo.setText("")
                    nota.setText("")
                }
                .addOnFailureListener { _ -> mensaje.text = "ERROR AL GUARDAR" }
        }else{
            mensaje.text = "DEBES LLENAR LOS CAMPOS"
        }

    }
    fun visualizar(b: View){
        val button: Button = b as Button
        var datos = ""
        db.collection("notas").get().addOnSuccessListener {
            resultado -> for(documento in resultado){
                datos += "Titulo: ${documento.data["titulo"]} --- Nota: ${documento.data["nota"]}\n "
            }
            viewer.text = datos
        }
            .addOnFailureListener { exception ->
                viewer.text = "No se pudo cargar las notas"
            }

    }
}