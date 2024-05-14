package com.example.game_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.game_app.Session.LoginPref

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername :EditText
    private lateinit var btnLogin : Button

    lateinit var session : LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = LoginPref(this)

        if (session.isLoggedIn()){
            var i : Intent = Intent(applicationContext,MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }

        etUsername = findViewById(R.id.etUsername)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener{
            var username = etUsername.text.toString().trim()

            if(username.isEmpty() ){
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
            }else{

                session.createLoginSession(username)
                var i : Intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }

    }
}