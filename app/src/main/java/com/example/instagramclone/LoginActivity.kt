package com.example.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT

        val signup : Button = findViewById(R.id.signup_button)
        signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            if (binding.mobileEmail.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")){
                Toast.makeText(this@LoginActivity, "Please fill all the details.", Toast.LENGTH_SHORT).show()
            }
            else{
                var user = User(binding.mobileEmail.editText?.text.toString(),
                    binding.password.editText?.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.mobileEmail!!, user.password!!).addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}