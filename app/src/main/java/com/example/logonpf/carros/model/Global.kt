package com.example.logonpf.carros.model
import com.google.firebase.database.*

/**
 * Created by Roberto on 18/04/2018.
 */


 class Global {

    companion object {
        lateinit var database : FirebaseDatabase
        lateinit var clinicaRef : DatabaseReference
        lateinit var clinicaAtual : Carro
        fun configurarFirebase(){
            database = FirebaseDatabase.getInstance("https://carproject-7daf0.firebaseio.com/")
            clinicaRef = database.getReference("Clinica")
        }
    }
}