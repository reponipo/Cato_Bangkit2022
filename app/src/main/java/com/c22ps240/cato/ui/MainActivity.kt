package com.c22ps240.cato.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.c22ps240.cato.databinding.ActivityIntroSliderBinding
import com.c22ps240.cato.databinding.ActivityMainBinding
import com.c22ps240.cato.language.MyContextWrapper
import com.c22ps240.cato.language.MyPreference
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    lateinit var myPreference: MyPreference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ss:String = intent.getStringExtra("key").toString()
        binding.nama.setText(ss)
        binding.buttonScan.setOnClickListener{
            val intent = Intent(this, ImageResultActivity::class.java)
            startActivity(intent)
        }

        var current = LocalDateTime.now()
        var fullLocaleFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        var fullLocaleTime = current.format(fullLocaleFormat)


        binding.tanggal.text = fullLocaleTime

    }

    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLanguage()
        super.attachBaseContext(lang?.let { MyContextWrapper.wrap(newBase, it) })
    }
}