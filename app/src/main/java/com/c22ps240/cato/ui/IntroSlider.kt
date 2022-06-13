package com.c22ps240.cato.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.c22ps240.cato.R
import com.c22ps240.cato.databinding.ActivityIntroSliderBinding
import com.c22ps240.cato.databinding.ActivityMainBinding
import com.c22ps240.cato.language.MyContextWrapper
import com.c22ps240.cato.language.MyPreference

class IntroSlider : AppCompatActivity() {

    lateinit var myPreference: MyPreference
    lateinit var context: Context

    private lateinit var binding: ActivityIntroSliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this
        myPreference = MyPreference(this)

        val lang = myPreference.getLanguage()


        if(lang == "in" ){
            binding.bahasaIndo.setBackgroundResource(R.drawable.shape_rounded2)
            binding.bahasaEng.setBackgroundResource(R.drawable.shape_rounded)
        }
        else if( lang == "en"){
            binding.bahasaEng.setBackgroundResource(R.drawable.shape_rounded2)
            binding.bahasaIndo.setBackgroundResource(R.drawable.shape_rounded)
        }

        setupView()

        binding.bahasaIndo.setOnClickListener{
            myPreference.setLanguage("in")
            relod()
        }

        binding.bahasaEng.setOnClickListener{
            myPreference.setLanguage("en")
            relod()
        }

        binding.btnUserName.setOnClickListener{
            val nama1 = findViewById<EditText>(R.id.et_userName).text.toString()


            val it = Intent(this, MainActivity::class.java)
            it.putExtra("key", nama1)
            startActivity(it)
            finish()
        }
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLanguage()
        super.attachBaseContext(lang?.let { MyContextWrapper.wrap(newBase, it) })
    }

    private fun relod(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }

}