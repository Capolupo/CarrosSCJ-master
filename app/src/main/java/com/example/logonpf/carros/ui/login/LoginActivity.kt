package com.example.logonpf.carros.ui.login

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import com.example.logonpf.carros.R

/**
 * Created by Roberto on 17/04/2018.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var photo : ImageView;
    lateinit var email : EditText;
    lateinit var senha : EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);
        photo = findViewById(R.id.login_imagem);
        email = findViewById(R.id.login_email);
        senha = findViewById(R.id.login_password);
    }
}