package com.example.logonpf.carros.ui.login

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
import android.util.Log
import android.widget.*
import com.example.logonpf.carros.model.Carro
import com.example.logonpf.carros.model.Global
import com.google.firebase.database.*


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

    lateinit var mFirebaseRemoteConfig : FirebaseRemoteConfig;

    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this

        photo = findViewById(R.id.login_imagem)
        email = findViewById(R.id.login_email)
        senha = findViewById(R.id.login_password)
        entrar = findViewById(R.id.login_entrar)
        cadastre = findViewById(R.id.login_cadastrar)
        cadastre.setOnClickListener { cadastrar() }

        entrar.setOnClickListener { entrar() }

        configurarFirebase()
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