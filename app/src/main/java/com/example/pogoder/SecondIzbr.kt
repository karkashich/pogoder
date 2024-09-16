package com.example.pogoder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject


class SecondIzbr : Activity(), View.OnClickListener {
    private lateinit var searchEditText: EditText
    private lateinit var mPrevButton: Button
    private lateinit var mNextButton: Button
    private val apiKey = "16e6d89d1a356ebab71e8feb97dc49c0"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        mPrevButton = findViewById(R.id.prev_button)
        mNextButton = findViewById(R.id.next_button)

        mPrevButton.setOnClickListener(this)
        mNextButton.setOnClickListener(this)

        searchEditText = findViewById(R.id.searchEditText)

        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            fetchWeather(query)
        }
    }
    private fun fetchWeather(city: String) {
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&lang=ru&appid=$apiKey"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                displayWeather(response)
            },
            { error ->
                Toast.makeText(this, "Данные не найдены.", Toast.LENGTH_SHORT).show()
            }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun displayWeather(data: JSONObject) {
        val name = data.getString("name")
        val weather = data.getJSONArray("weather").getJSONObject(0)
        val icon = weather.getString("icon")
        val description = weather.getString("description")
        val main = data.getJSONObject("main")
        val temp = main.getDouble("temp")
        val humidity = main.getInt("humidity")
        val feelsLike = main.getDouble("feels_like")
        val wind = data.getJSONObject("wind")
        val speed = wind.getDouble("speed")

        Log.d("Weather", data.toString())

        findViewById<TextView>(R.id.city).text = "Погода в $name"
        Picasso.get().load("https://openweathermap.org/img/wn/$icon.png").into(findViewById<ImageView>(R.id.icon))
        findViewById<TextView>(R.id.description).text = description
        findViewById<TextView>(R.id.temp).text = "$temp°C"
        findViewById<TextView>(R.id.humidity).text = "Влажность: $humidity%"
        findViewById<TextView>(R.id.feels_like).text = "Ощущается как: $feelsLike°C"
        findViewById<TextView>(R.id.wind).text = "Скорость ветра: $speed Км/ч"

    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.prev_button -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.next_button -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
