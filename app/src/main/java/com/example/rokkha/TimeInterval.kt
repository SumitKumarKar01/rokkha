package com.example.rokkha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rokkha.databinding.ActivityTimeIntervalBinding

class TimeInterval : AppCompatActivity() {
    private lateinit var binding: ActivityTimeIntervalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeIntervalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val numberPicker = binding.numberPicker
        numberPicker.minValue = 1
        numberPicker.maxValue = 60
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            val text = "Changed from $oldVal to $newVal"
            Toast.makeText(this@TimeInterval, text, Toast.LENGTH_SHORT).show()
        }


    }
}