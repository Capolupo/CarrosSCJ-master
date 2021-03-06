package com.example.logonpf.carros.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import com.example.logonpf.carros.BuildConfig
import com.example.logonpf.carros.R
import com.example.logonpf.carros.ui.main.MainActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.*
import com.example.logonpf.carros.model.Carro
import com.example.logonpf.carros.model.Global
import com.example.logonpf.carros.model.Global.Companion.RC_SIGN_IN
import com.example.logonpf.carros.model.Global.Companion.providers
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult






/**
 * Created by Roberto on 17/04/2018.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var photo : ImageView;
    lateinit var email : EditText;
    lateinit var senha : EditText;
    lateinit var entrar : Button;
    lateinit var cadastre : TextView;

    lateinit var myRef : DatabaseReference;
    lateinit var clinicaCar : Carro;


    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        Global.mAuth = FirebaseAuth.getInstance();

        photo = findViewById(R.id.login_imagem)
        email = findViewById(R.id.login_email)
        senha = findViewById(R.id.login_password)
        entrar = findViewById(R.id.login_entrar)
        cadastre = findViewById(R.id.login_cadastrar)
        cadastre.setOnClickListener { cadastrar() }

        entrar.setOnClickListener { loginFirebase() }

        configurarFirebase()
        //loginFirebase()
    }

    fun loginFirebase(){
        //val currentUser = Global.mAuth.currentUser
        //updateUI(currentUser)
        if (email.text.toString().isNullOrEmpty() || senha.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Campo e-mail e senha não podem ser vaziu.", Toast.LENGTH_LONG).show()
        }
        else{
            Global.mAuth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString())
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FragmentActivity.TAG", "signInWithEmail:success")
                            val user = Global.mAuth.getCurrentUser()
                            startActivity(Intent(context, MainActivity::class.java))

                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FragmentActivity.TAG", "signInWithEmail:failure", task.exception)
                            Toast.makeText(context, task.exception?.localizedMessage,
                                    Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }

                        // ...
                    })
        }
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .build(),
//                RC_SIGN_IN);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().getCurrentUser()

                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }






    fun configurarFirebase(){
        Global.configurarFirebase()
//        val database = FirebaseDatabase.getInstance("https://carproject-7daf0.firebaseio.com/")
//        myRef = database.getReference("Clinica")
        conectarAoFireBase()
    }

    fun conectarAoFireBase(){
//        myRef.addValueEventListener(object : ValueEventListener {
            Global.clinicaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("DataSnap", dataSnapshot.toString())
                if (dataSnapshot.value.toString() != "null"){
                    val clinicaFire = dataSnapshot.child("Clinica").getValue(Carro::class.java);
                    clinicaCar = clinicaFire ?: Carro("0","","",1,"","",0)
                }else {
//                    clinicaCar = Carro("0","marca","modelo",1,"placa","url",0)
//                    myRef.child("teste").setValue(clinicaCar)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("DataError", error.toString());
            }
        })
    }

    fun entrar(){
        Global.clinicaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("DataSnap", dataSnapshot.toString())
                if (dataSnapshot.value.toString() != "null"){
                    val emailCoded : String = email.text.toString().replace(".","2e")
                    if(dataSnapshot.hasChild(emailCoded)){
                        if(dataSnapshot.child(emailCoded).child("modelo").getValue().toString().equals(senha.text.toString())) {
                            val clinicaFire = dataSnapshot.child(emailCoded ?: "").getValue(Carro::class.java)
                            Global.clinicaAtual = clinicaFire ?: Carro("0", "", "", 1, "", "", 0)
                            startActivity(Intent(context, MainActivity::class.java))
                        }else
                            Toast.makeText(context,
                                    "Senha não confere.",
                                    Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,
                                "Email não cadastrado.",
                                Toast.LENGTH_LONG).show()
                    }
                }else {
//                    clinicaCar = Carro("0","marca","modelo",1,"placa","url",0)
//                    myRef.child("teste").setValue(clinicaCar)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("DataError", error.toString());
            }
        })
    }

    fun cadastrar(){
        startActivity(Intent(this, SignUp::class.java))
    }
}