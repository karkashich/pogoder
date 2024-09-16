package com.example.pogoder

data class WeatherData(
    val name: String,
    val iconUrl: String,
    val description: String,
    val temperature: Double,
    val humidity: Int,
    val feelsLike: Double,
    val windSpeed: Double

)
