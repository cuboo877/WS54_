import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.service.WeatherResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class WeatherViewModel : ViewModel() {
    val weatherLiveData = MutableLiveData<WeatherResponse?>()

    private val client = OkHttpClient()
    private val gson = Gson()

    init {
        reloadWeather("Taipei", 5*60*1000)
    }

    private fun reloadWeather(location: String, delayTime: Long) {
        val request = Request.Builder()
            .url("https://api.weatherapi.com/v1/forecast.json?key=9264efe425a74182ad4160515240602&q=$location&days=10&aqi=yes")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // handle the error
            }

            override fun onResponse(call: Call,response: Response) {
                response.body?.string()?.let { body ->
                    val result = gson.fromJson(body, WeatherResponse::class.java)
                    weatherLiveData.postValue(result)
                }
            }
        })
    }
}

/*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service.WeatherResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class WeatherViewModel: ViewModel() {
    val weatherLiveData = MutableLiveData<WeatherResponse?>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            reloadWeather("Taipei", 5*60*1000)
        }
    }

    private suspend fun loadWeather(location: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.weatherapi.com/v1/forecast.json?key=9264efe425a74182ad4160515240602&q=$location&days=10&aqi=yes")
            .build()

        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()

            if (!body.isNullOrEmpty()) {
                val gson = Gson()
                val result = gson.fromJson(body, WeatherResponse::class.java)
                weatherLiveData.postValue(result)
            }
        } catch (e: IOException) {
        }
    }

    private suspend fun reloadWeather(location: String, delayTime: Long) {
        while (true) {
            loadWeather(location)
            delay(delayTime)
        }
    }
}
*/