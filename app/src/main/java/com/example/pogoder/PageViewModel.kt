package com.example.pogoder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
class PageViewModel(): ViewModel() {
    private val _weatherLiveData = MutableLiveData<WeatherData>()
    private val _weatherLiveData1 = MutableLiveData<WeatherData>()
    private val _weatherLiveData2 = MutableLiveData<WeatherData>()
    private val _weatherLiveData3 = MutableLiveData<WeatherData>()
    val weatherLiveData: LiveData<WeatherData> = _weatherLiveData
    val weatherLiveData1: LiveData<WeatherData> = _weatherLiveData1
    val weatherLiveData2: LiveData<WeatherData> = _weatherLiveData2
    val weatherLiveData3: LiveData<WeatherData> = _weatherLiveData3
    fun fetchWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&lang=ru&appid=16e6d89d1a356ebab71e8feb97dc49c0").readText()
                var weatherData = parseWeather(data)
                _weatherLiveData.postValue(weatherData)

            } catch (e: Exception) {
                super.onCleared()
            }
        }
    }
    fun fetchWeather(city: String, screenNumber: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&lang=ru&appid=16e6d89d1a356ebab71e8feb97dc49c0").readText()
                var weatherData = parseWeather(data)
                _weatherLiveData.postValue(weatherData)
                when(screenNumber){
                    1 -> _weatherLiveData1.postValue(weatherData)
                    2 -> _weatherLiveData2.postValue(weatherData)
                    3 -> _weatherLiveData3.postValue(weatherData)
                }

            } catch (e: Exception) {
                super.onCleared()
            }
        }
    }
    fun fetchWeather1(firstparam:String,secondparam:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = URL("https://api.openweathermap.org/data/2.5/weather?lat=$firstparam&lon=$secondparam&units=metric&lang=ru&appid=16e6d89d1a356ebab71e8feb97dc49c0").readText()
                val weatherData = parseWeather(data)
                _weatherLiveData.postValue(weatherData)

            } catch (e: Exception) {
                super.onCleared()
            }
        }
    }
    private fun parseWeather(data: String): WeatherData {
        val json = JSONObject(data)
        val name = json.getString("name")
        val weather = json.getJSONArray("weather").getJSONObject(0)
        val icon = weather.getString("icon")
        val description = weather.getString("description")
        val main = json.getJSONObject("main")
        val temp = main.getDouble("temp")
        val humidity = main.getInt("humidity")
        val feelsLike = main.getDouble("feels_like")
        val wind = json.getJSONObject("wind")
        val speed = wind.getDouble("speed")
        return WeatherData(name, icon, description, temp, humidity, feelsLike, speed)
    }

    fun updateTextValue(newValue: String): Int {
        val value = when (newValue) {
            "облачно с прояснениями" -> R.drawable.midcloud
            "дождь" -> R.drawable.rain
            "переменная облачность" -> R.drawable.fewcloud
            "небольшой дождь" -> R.drawable.fewrain
            "ясно" -> R.drawable.clear
            "гроза" -> R.drawable.thunder
            "пасмурно" -> R.drawable.cloud
            "мгла" -> R.drawable.mist
            else -> R.drawable.fonmain
        }
        return value
    }

}
