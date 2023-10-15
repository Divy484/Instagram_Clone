package com.example.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT

        user = User()

        val login : Button = findViewById(R.id.login_button)
        login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }

        binding.signupButton.setOnClickListener {
            if (binding.mobileEmail.editText?.text.toString().equals("") or
                binding.name.editText?.text.toString().equals("") or
                binding.username.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")) {
                Toast.makeText(this@SignUpActivity, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
            else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.mobileEmail.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful){
                        user.mobileEmail = binding.mobileEmail.editText?.text.toString()
                        user.name = binding.name.editText?.text.toString()
                        user.username = binding.username.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        Firebase.firestore.collection("User").document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                            Toast.makeText(this@SignUpActivity, "Registration Successful.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
                            finish()
                        }
                    }
                    else {
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}