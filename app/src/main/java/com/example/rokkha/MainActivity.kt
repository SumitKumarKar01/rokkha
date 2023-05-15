package com.example.rokkha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rokkha.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import io.ktor.server.routing.RoutingPath.Companion.root

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)

        auth = FirebaseAuth.getInstance()

        checkIfUserIsLogged()

        binding.btnLogin.setOnClickListener {
            registerUser()
        }
    }

    private fun checkIfUserIsLogged() {
        if (auth.currentUser != null) {
            startActivity(Intent(this, Alert::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Alert::class.java))
                        finish()
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { signInTask ->
                                if (signInTask.isSuccessful) {
                                    startActivity(Intent(this, Alert::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
        } else {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private val ActivityMainBinding.rootView get() = root
}
