package com.example.ws54_compose_prac1

import WeatherViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.page.HomePage
import com.example.page.RegionPage
import com.example.ws54_compose_prac1.ui.theme.Ws54_compose_prac1Theme

class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ws54_compose_prac1Theme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    val weatherData = weatherViewModel.weatherLiveData.observeAsState(null)
                    weatherData.value?.let {weatherResponse ->
                        NavHost(navController = navController, startDestination = "Home") {
                            composable("Home") {
                                HomePage(navController, weatherResponse)
                            }
                            composable("Region") {
                                RegionPage(navController, weatherResponse)
                            }
                        }
                    }
                }
            }
        }
    }
}
