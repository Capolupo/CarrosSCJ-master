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
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import android.widget.Toast
import com.example.logonpf.carros.ui.main.MainActivity
import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener



/**
 * Created by Roberto on 17/04/2018.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var photo : ImageView;
    lateinit var email : EditText;
    lateinit var senha : EditText;
    lateinit var entrar : Button;

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
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        mFirebaseRemoteConfig.setConfigSettings(configSettings)

        conectarAoFireBase()
    }

    fun conectarAoFireBase(){
        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Fetch Succeeded",
                                Toast.LENGTH_SHORT).show()

                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        mFirebaseRemoteConfig.activateFetched()
                    } else {
                        Toast.makeText(this, "Fetch Failed",
                                Toast.LENGTH_SHORT).show()
                    }

                }
    }

    fun entrar(){
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}