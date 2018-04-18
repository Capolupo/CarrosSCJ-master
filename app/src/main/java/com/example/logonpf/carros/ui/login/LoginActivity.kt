package com.example.logonpf.carros.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.logonpf.carros.BuildConfig
import com.example.logonpf.carros.R
import com.example.logonpf.carros.ui.main.MainActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener




/**
 * Created by Roberto on 17/04/2018.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var photo : ImageView;
    lateinit var email : EditText;
    lateinit var senha : EditText;
    lateinit var entrar : Button;
    lateinit var myRef : DatabaseReference;

    lateinit var mFirebaseRemoteConfig : FirebaseRemoteConfig;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);
        photo = findViewById(R.id.login_imagem);
        email = findViewById(R.id.login_email);
        senha = findViewById(R.id.login_password);
        entrar = findViewById(R.id.login_entrar);
        entrar.setOnClickListener { entrar() };

        configurarFirebase()
    }

    fun configurarFirebase(){
        val database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Clinica")
        conectarAoFireBase()
    }

    fun conectarAoFireBase(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun entrar(){
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}