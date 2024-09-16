package com.example.pogoder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pogoder.databinding.AuthorsBinding

class AuthorsPage: AppCompatActivity() {
    private lateinit var binding: AuthorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.helppage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://qiwi.com/payment/form/99999?extra[%27accountType%27]=phone&extra%5B%27account%27%5D=79115582763&amountInteger=1000&amountFraction=0&extra%5B%27comment%27%5D=%CD%E0%20%EF%EE%E4%E4%E5%F0%E6%EA%F3%20%EF%F0%EE%E5%EA%F2%E0&currency=643&blocked[0]=account"))
            startActivity(intent)
        }
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, AuthorsPage::class.java)
            startActivity(intent)
        }

        binding.imageButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imageButton3.setOnClickListener {
            val intent = Intent(this, WeatherList::class.java)
            startActivity(intent)
        }
    }
    fun onClick(v: View?) {

    }
}