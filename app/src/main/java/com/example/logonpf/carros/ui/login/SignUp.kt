package com.example.logonpf.carros.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.logonpf.carros.R
import com.example.logonpf.carros.model.Carro
import com.example.logonpf.carros.model.Global

/**
 * Created by Roberto on 18/04/2018.
 */
class SignUp : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var senha : EditText
    lateinit var confSenha : EditText
    lateinit var cadastrar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siginup)

        email = findViewById(R.id.signup_email)
        senha = findViewById(R.id.signup_senha)
        confSenha = findViewById(R.id.signup_confsenha)
        cadastrar = findViewById(R.id.signup_cadastrar)

        cadastrar.setOnClickListener{ cadastrarClinica() }
    }

    fun cadastrarClinica(){
        if(senha.text.toString().equals(confSenha.text.toString())){
            try {
                val clinica = Carro("0", email.text.toString(), senha.text.toString(), 0, "placa", "url", 50)
                Global.clinicaRef.child(email.text.toString().replace(".","2e")).setValue(clinica)
            }catch (e: Exception) {
                Log.e("ErroFirebase", e.toString())
            }
        }
        else{
            Toast.makeText(this,
                    "Senhas não conferem.\nPor favor digite a mesma\nsenha nos dois campos.",
                    Toast.LENGTH_LONG).show()
        }

    }


}