package com.example.logonpf.carros.model
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import java.util.Arrays.asList



/**
 * Created by Roberto on 18/04/2018.
 */


 class Global {

    companion object {
        lateinit var database : FirebaseDatabase
        lateinit var clinicaRef : DatabaseReference
        lateinit var clinicaAtual : Carro
        lateinit var mAuth : FirebaseAuth


        var RC_SIGN_IN = 123
        var providers = Arrays.asList(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build())

        fun configurarFirebase(){
            database = FirebaseDatabase.getInstance("https://carproject-7daf0.firebaseio.com/")
            clinicaRef = database.getReference("Clinica")
        }
    }
}