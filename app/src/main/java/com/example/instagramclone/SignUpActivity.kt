package com.example.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){
                if (it == null){

                }
                else {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
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

        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE",-1) == 1){
                binding.signupButton.text = "Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    user = it.toObject<User>()!!
                    if (!user.image.isNullOrEmpty()){
                        Picasso.get().load(user.image).into(binding.profileImage)
                    }
                    binding.mobileEmail.editText?.setText(user.mobileEmail)
                    binding.name.editText?.setText(user.name)
                    binding.username.editText?.setText(user.username)
                    binding.password.editText?.setText(user.password)
                }
            }
        }

        binding.signupButton.setOnClickListener {
            if(intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE",-1) == 1){
                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                        Toast.makeText(this@SignUpActivity, "Profile Updated.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
                        finish()
                    }
                }
            }
            else {

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
                            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
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

        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
    }
}