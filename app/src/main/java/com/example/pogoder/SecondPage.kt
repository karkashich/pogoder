package com.example.pogoder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogoder.databinding.ActivitySecondBinding
import com.squareup.picasso.Picasso
import java.io.File



class SecondPage: AppCompatActivity(),  View.OnClickListener {
    private lateinit var viewModel: PageViewModel
    private lateinit var searchEditText: EditText
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide()
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        binding.searchEditText1

        val file = File(applicationContext.filesDir, "File.txt")
        if (file.exists()) {
            val text = File(filesDir, "File.txt").readText(Charsets.UTF_8)
            try {
                viewModel.fetchWeather(text)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Данные не найдены.", Toast.LENGTH_SHORT).show()
            }


        }
        binding.searchButton1.setOnClickListener {

            val query = searchEditText.text.toString()

            openFileOutput("File.txt", Context.MODE_PRIVATE).use {
                File(filesDir, "File.txt").writeText(query, Charsets.UTF_8)
            }

            viewModel.fetchWeather(query)
        }

        viewModel.weatherLiveData.observe(this, Observer { weatherData ->
            binding.city1.text = "${weatherData.name}"
            Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png")
                .into(binding.icon)
            binding.description1.text = weatherData.description
            binding.Sec.setBackgroundResource(viewModel.updateTextValue(weatherData.description))
            binding.temp1.text = "${weatherData.temperature.toInt()}°C"
            binding.humidity1.text = "Влажность: ${weatherData.humidity}%"
            binding.feelslike.text = "Ощущается как: ${weatherData.feelsLike}°C"
            binding.wind1.text = "Скорость ветра: ${weatherData.windSpeed} Км/ч"
        })
        binding.prevButton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.nextButton1.setOnClickListener {
            val intent = Intent(this, ThirdPage::class.java)
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

    override fun onClick(v: View) {
    }}
//    private lateinit var searchEditText: EditText
//    private lateinit var mPrevButton: ImageButton
//    private lateinit var mNextButton: ImageButton
//    private val fileName = "myFile.txt"
//    private val apiKey = "16e6d89d1a356ebab71e8feb97dc49c0"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        getSupportActionBar()?.hide()
//        setContentView(R.layout.activity_second)
//        mPrevButton = findViewById(R.id.prev_button1)
//        mNextButton = findViewById(R.id.next_button1)
//        mPrevButton.setOnClickListener(this)
//        mNextButton.setOnClickListener(this)
//        searchEditText = findViewById(R.id.searchEditText1)
//        val context = applicationContext // или любой другой допустимый объект контекста
//        val file = File(context.filesDir, "myFile.txt")
//        if (file.exists()) {
//            val text = File(filesDir, "myFile.txt").readText(Charsets.UTF_8)
//            if (text != null) {
//                fetchWeather1(text)
//            }
//            val searchButton: ImageButton = findViewById(R.id.searchButton1)
//            searchButton.setOnClickListener {
//                val query = searchEditText.text.toString()
//                openFileOutput(fileName, Context.MODE_PRIVATE).use {
//                    File(filesDir, "myFile.txt").writeText(query, Charsets.UTF_8)
//                }
//                fetchWeather1(query)
//
//            }
//
//        }
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
//            findViewById<TextView>(R.id.city1).text = "Погода в $name"
//            Picasso.get().load("https://openweathermap.org/img/wn/$icon.png").into(findViewById<ImageView>(R.id.icon))
//            findViewById<TextView>(R.id.description1).text = description
//            findViewById<TextView>(R.id.temp1).text = "$temp°C"
//            findViewById<TextView>(R.id.humidity1).text = "Влажность: $humidity%"
//            findViewById<TextView>(R.id.feels_like1).text = "Ощущается как: $feelsLike°C"
//            findViewById<TextView>(R.id.wind1).text = "Скорость ветра: $speed Км/ч"
//        }
//    }
//
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.prev_button1 -> {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.next_button1 -> {
//                val intent = Intent(this, ThirdPage::class.java)
//                startActivity(intent)
//            }
//        }
//    }
//    private object NoOpContinuation : Continuation<Any?> {
//        override val context: CoroutineContext = EmptyCoroutineContext
//
//        override fun resumeWith(result: Result<Any?>) {
//            // Nothing
//        }
//    }
