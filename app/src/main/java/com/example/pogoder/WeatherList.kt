package com.example.pogoder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogoder.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import java.io.File

class WeatherList : AppCompatActivity() {
    private lateinit var viewModel: PageViewModel
    private lateinit var binding: ActivityWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        val city = File(filesDir, "FileMain.txt").readText(Charsets.UTF_8)
        val city2 = File(applicationContext.filesDir, "File.txt")
        if (city2.exists()) {
            val city2 = File(filesDir, "File.txt").readText(Charsets.UTF_8)
            try {
                viewModel.fetchWeather(city2, 2)
                viewModel.weatherLiveData2.observe(this, Observer { weatherData ->
                    // Обновляем UI данными о погоде
                    binding.city2.text = "${weatherData.name}"
                    Picasso.get()
                        .load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png")
                        .into(binding.icon2)
                    binding.temp2.text = "${weatherData.temperature.toInt()}°C"
                })
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Данные не найдены.", Toast.LENGTH_SHORT).show()
            }
        }
        val city3 = File(applicationContext.filesDir, "myFile1.txt")
        if (city3.exists()) {
            val city3 = File(filesDir, "myFile1.txt").readText(Charsets.UTF_8)
            if (city3 != null) {
                viewModel.fetchWeather(city3, 3)
                viewModel.weatherLiveData3.observe(this, Observer { weatherData ->
                    // Обновляем UI данными о погоде
                    binding.city3.text = "${weatherData.name}"
                    Picasso.get()
                        .load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png")
                        .into(binding.icon3)
                    binding.temp3.text = "${weatherData.temperature.toInt()}°C"
                })
            }
        }
        viewModel.fetchWeather(city, 1)
        viewModel.weatherLiveData1.observe(this, Observer { weatherData ->
            // Обновляем UI данными о погоде
            binding.city1.text = "${weatherData.name}"
            Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png")
                .into(binding.icon1)
            binding.temp1.text = "${weatherData.temperature.toInt()}°C"
        })
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