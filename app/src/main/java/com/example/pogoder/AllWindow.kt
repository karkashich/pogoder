package com.example.pogoder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import java.io.File

class AllWindow : AppCompatActivity() {
    private lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all)
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        val city = File(filesDir, "FileMain.txt").readText(Charsets.UTF_8)
        val city2 = File(applicationContext.filesDir, "File.txt")
        if (city2.exists()) {
            val city2 = File(filesDir, "File.txt").readText(Charsets.UTF_8)
            try {
                viewModel.fetchWeather(city2, 2)
                viewModel.weatherLiveData2.observe(this, Observer { weatherData ->
                    // Обновляем UI данными о погоде
                    findViewById<TextView>(R.id.city2).text = "${weatherData.name}"
                    Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png").into(findViewById<ImageView>(R.id.icon2))
                    findViewById<TextView>(R.id.temp2).text = "${weatherData.temperature.toInt()}°C"
                })
            } catch (e: Exception){
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
                    findViewById<TextView>(R.id.city3).text = "${weatherData.name}"
                    Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png").into(findViewById<ImageView>(R.id.icon3))
                    findViewById<TextView>(R.id.temp3).text = "${weatherData.temperature.toInt()}°C"
                })
            }
        }
        viewModel.fetchWeather(city, 1)
        viewModel.weatherLiveData1.observe(this, Observer { weatherData ->
            // Обновляем UI данными о погоде
            findViewById<TextView>(R.id.city1).text = "${weatherData.name}"
            Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png").into(findViewById<ImageView>(R.id.icon1))
            findViewById<TextView>(R.id.temp1).text = "${weatherData.temperature.toInt()}°C"
        })
    }
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageButton -> {
                val intent = Intent(this, AuthorsPage::class.java)
                startActivity(intent)
            }
            R.id.imageButton2 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.imageButton3 -> {
                val intent = Intent(this, AllWindow::class.java)
                startActivity(intent)
            }
        }
    }
}

