package com.example.rokkha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.rokkha.databinding.ActivitySecondBinding
import com.google.firebase.auth.FirebaseAuth

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var user:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val proceedButton = findViewById<Button>(R.id.proceedbutton)
        proceedButton.setOnClickListener{
            val Intent = Intent(this,Alert::class.java)
            startActivity(Intent)
        }
        user = FirebaseAuth.getInstance()
        if (user.currentUser != null){
            user.currentUser?.let {

                binding.tvUserEmail.text = it.email


            }

        }

        binding.btnSignout.setOnClickListener{
            logout()
        }



    }
    fun logout(){
        user.signOut()
        startActivity(
            Intent(this,MainActivity::class.java)
        )
        finish()

    }

}