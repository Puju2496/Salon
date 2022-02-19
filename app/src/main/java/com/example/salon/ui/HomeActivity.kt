package com.example.salon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.salon.R
import com.example.salon.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.bind(findViewById(R.id.root))
    }

}