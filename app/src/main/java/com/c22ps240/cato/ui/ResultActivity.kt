package com.c22ps240.cato.ui

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.c22ps240.cato.databinding.ActivityMainBinding
import com.c22ps240.cato.databinding.ActivityResultBinding
import java.io.File

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            val it = Intent(this, ImageResultActivity::class.java)
            startActivity(it)
            finish()
        }

        binding.btnHome.setOnClickListener{
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
        }

}

}