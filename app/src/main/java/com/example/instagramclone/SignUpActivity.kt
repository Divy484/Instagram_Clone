package com.example.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.statusBarColor = Color.TRANSPARENT

        val login : Button = findViewById(R.id.login_button)
        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}