package com.example.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = Color.TRANSPARENT

        val signup : Button = findViewById(R.id.signup_button)
        signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}