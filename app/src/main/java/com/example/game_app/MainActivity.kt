package com.example.game_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.game_app.Session.LoginPref

class MainActivity : AppCompatActivity() {

    private  lateinit var tvUsername :TextView
    private lateinit var btnLogout :Button

    lateinit var session: LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        session = LoginPref(this)
        tvUsername = findViewById(R.id.tvUsername)
        btnLogout = findViewById(R.id.btnLogout)

        session.checkLogin()

        var user : HashMap<String ,String> = session.getUserDetails()

        var username = user.get(LoginPref.KEY_USERNAME)

        tvUsername.setText(username)
        btnLogout.setOnClickListener{
            session.LogoutUser()
            navigateToGameActivity()
        }

    }
    private fun navigateToGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity if needed
    }
}