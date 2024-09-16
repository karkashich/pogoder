package com.example.pogoder

import android.Manifest
import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogoder.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import java.io.File


class MainActivity : AppCompatActivity(),  View.OnClickListener, LocationListener {
    private lateinit var viewModel: PageViewModel
    private lateinit var searchEditText: EditText
    private lateinit var mPrevButton: ImageButton
    private lateinit var mNextButton: ImageButton
    private lateinit var binding: ActivityMainBinding
    private val locationPermissionCode = 2
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)

        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        mPrevButton = findViewById(com.example.pogoder.R.id.prev_button)
        mNextButton = findViewById(com.example.pogoder.R.id.next_button)
        searchEditText = findViewById(com.example.pogoder.R.id.searchEditText)
        val searchButton: ImageButton = findViewById(com.example.pogoder.R.id.searchButton)
        searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            viewModel.fetchWeather(query)
        }
        viewModel.weatherLiveData.observe(this, Observer { weatherData ->
            openFileOutput("FileMain.txt", Context.MODE_PRIVATE).use {
                File(filesDir, "FileMain.txt").writeText(weatherData.name, Charsets.UTF_8)
            }
            binding.city.text = "${weatherData.name}"
            Picasso.get().load("https://openweathermap.org/img/wn/${weatherData.iconUrl}.png")
                .into(binding.icon)
            binding.description.text = weatherData.description
            binding.Main.setBackgroundResource(viewModel.updateTextValue(weatherData.description))
            binding.temp.text = "${weatherData.temperature.toInt()}°C"
            binding.humidity.text = "Влажность: ${weatherData.humidity}%"
            binding.feelslike.text = "Ощущается как: ${weatherData.feelsLike}°C"
            binding.wind.text = "Скорость ветра: ${weatherData.windSpeed} Км/ч"
        })
        binding.prevButton.setOnClickListener {
            val intent = Intent(this, ThirdPage::class.java)
            startActivity(intent)
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, SecondPage::class.java)
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
//    private fun getLocation() {
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
//    }
    override fun onLocationChanged(location: Location) {
        viewModel.fetchWeather1(location.latitude.toString(), location.longitude.toString())

    }
    override fun onClick(v: View?) {

            }
    }


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
//    private fun fetchWeather(firstparam:String,secondparam:String) {
//        Thread(Runnable {
//            val url =
//                "https://api.openweathermap.org/data/2.5/weather?lat=$firstparam&lon=$secondparam&units=metric&lang=ru&appid=$apiKey"
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
//            findViewById<TextView>(R.id.city).text = name
//            Picasso.get().load("https://openweathermap.org/img/wn/$icon.png")
//                .into(findViewById<ImageView>(R.id.icon))
//            findViewById<TextView>(R.id.description).text = description
//            findViewById<TextView>(R.id.temp).text = "$temp°C"
//            findViewById<TextView>(R.id.humidity).text = "Влажность: $humidity%"
//            findViewById<TextView>(R.id.feels_like).text = "Ощущается как: $feelsLike°C"
//            findViewById<TextView>(R.id.wind).text = "Скорость ветра: $speed Км/ч"
//        }
//    }

