package com.example.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.instagramclone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT

        val login : Button = findViewById(R.id.login_button)
        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            if (binding.mobileEmail.editText?.text.toString().equals("") or
                binding.name.editText?.text.toString().equals("") or
                binding.username.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")) {
                Toast.makeText(this@SignUpActivity, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}