package com.example.pogoder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogoder.databinding.ThirdPageBinding
import com.squareup.picasso.Picasso
import java.io.File

class ThirdPage: AppCompatActivity(),  View.OnClickListener {
    private lateinit var viewModel: PageViewModel
    private lateinit var binding: ThirdPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ThirdPageBinding.inflate(layoutInflater)
        setContentView(R.layout.third_page)
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)

        val file = File(applicationContext.filesDir, "myFile1.txt")
        if (file.exists()) {
            val text = File(filesDir, "myFile1.txt").readText(Charsets.UTF_8)
            if (text != null) {
                viewModel.fetchWeather(text)
            }
        }

        binding.searchButton1.setOnClickListener {
            val query = binding.searchEditText2.text.toString()

            openFileOutput("myFile1.txt", Context.MODE_PRIVATE).use {
                File(filesDir, "myFile1.txt").writeText(query, Charsets.UTF_8)
            }
            viewModel.fetchWeather(query)
        }
        viewModel.weatherLiveData.observe(this, Observer { weatherData ->
            binding.city2.text = "${weatherData.name}"
            Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png")
                .into(binding.icon)
            binding.description2.text = weatherData.description
            binding.Third.setBackgroundResource(viewModel.updateTextValue(weatherData.description))
            binding.temp2.text = "${weatherData.temperature.toInt()}°C"
            binding.humidity2.text = "Влажность: ${weatherData.humidity}%"
            binding.feelsLike2.text = "Ощущается как: ${weatherData.feelsLike}°C"
            binding.wind2.text = "Скорость ветра: ${weatherData.windSpeed} Км/ч"
        })

        binding.prevButton2.setOnClickListener {
            val intent = Intent(this, SecondPage::class.java)
            startActivity(intent)
        }

        binding.nextButton2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
//    private lateinit var searchEditText: EditText
//    private lateinit var mPrevButton: ImageButton
//    private lateinit var mNextButton: ImageButton
//    private val fileName = "myFile1.txt"
//    private val apiKey = "16e6d89d1a356ebab71e8feb97dc49c0"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.third_page)
//        getSupportActionBar()?.hide()
//        mPrevButton = findViewById(R.id.prev_button2)
//        mNextButton = findViewById(R.id.next_button2)
//        mPrevButton.setOnClickListener(this)
//        mNextButton.setOnClickListener(this)
//        searchEditText = findViewById(R.id.searchEditText2)
//        val context = applicationContext // или любой другой допустимый объект контекста
//        val file = File(context.filesDir, "myFile1.txt")
//        if (file.exists()) {
//            val text = File(filesDir, "myFile1.txt").readText(Charsets.UTF_8)
//            if (text != null) {
//                fetchWeather1(text)
//            }
//        val searchButton: ImageButton = findViewById(R.id.searchButton2)
//        searchButton.setOnClickListener {
//            val query = searchEditText.text.toString()
//            openFileOutput(fileName, Context.MODE_PRIVATE).use {
//                File(filesDir, "myFile1.txt").writeText(query, Charsets.UTF_8)
//            }
//            fetchWeather1(query)
//        }
//    }
//    }
//    private fun fetchWeather1(city: String) {
//        Thread(Runnable {
//            val url =
//                "https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&lang=ru&appid=$apiKey"
//            val request = JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null,
//                { response ->
//                    displayWeather(response)
//                },
//                { error ->
//                    Toast.makeText(this, "Данные не найдены.", Toast.LENGTH_SHORT).show()
//                }
//            )
//            Volley.newRequestQueue(this).add(request)
//        }).start()
//    }
//    private fun displayWeather(data: JSONObject) {
//        val name = data.getString("name")
//        val weather = data.getJSONArray("weather").getJSONObject(0)
//        val icon = weather.getString("icon")
//        val description = weather.getString("description")
//        val main = data.getJSONObject("main")
//        val temp = main.getDouble("temp")
//        val humidity = main.getInt("humidity")
//        val feelsLike = main.getDouble("feels_like")
//        val wind = data.getJSONObject("wind")
//        val speed = wind.getDouble("speed")
//        runOnUiThread {
//            findViewById<TextView>(R.id.city2).text = "Погода в $name"
//            Picasso.get().load("https://openweathermap.org/img/wn/$icon.png")
//                .into(findViewById<ImageView>(R.id.icon))
//            findViewById<TextView>(R.id.description2).text = description
//            findViewById<TextView>(R.id.temp2).text = "$temp°C"
//            findViewById<TextView>(R.id.humidity2).text = "Влажность: $humidity%"
//            findViewById<TextView>(R.id.feels_like2).text = "Ощущается как: $feelsLike°C"
//            findViewById<TextView>(R.id.wind2).text = "Скорость ветра: $speed Км/ч"
//        }
//    }
//
//
//
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.prev_button2 -> {
//                val intent = Intent(this, SecondPage::class.java)
//                startActivity(intent)
//            }
//            R.id.next_button2 -> {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }

