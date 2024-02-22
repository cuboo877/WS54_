package com.example.service

data class WeatherResponse(
    val location: location,
    val current: currency,
    val forecast: forecast,
)

data class location(
    val name:String,
    val region:String,
    val country: String,
    val localTime: String,
    val tz_id:String
)
//現在溫度、濕度...
data class currency(
    val last_update:String,
    val temp_c:Int,
    val temp_f:Float,
    val feelslike_c: Float,
    val is_day:Int,
    val uv: Double,
    val condition: _condition,
    val humidity:Int,
    val air_quality: _air_quality
)
//預計(從今日計算開始)
data class forecast(
    val forecastday:List<forecastDay>
)

data class forecastDay(
    val date: String,
    val day: dayData,
    val astro:astro,
    val hour: List<hourData>
)
//日計算:平均最高最低......
data class dayData(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avghumidity: Int,
    val daily_will_it_rain: Int,
    val daily_chance_of_rain: String,
    val daily_will_it_snow: Int,
    val condition: _condition,
    val uv: Double,
)
//今日日落日出...
data class astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: String
)

//今日每小時
data class Hour(
    val hour:List<hourData>
)

data class hourData(
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: _condition,
    val humidity: Int,
    val daily_will_it_rain: Int,
    val daily_chance_of_rain: String,
    val daily_will_it_snow: Int,
    val uv: Double,
    val air_quality: _air_quality

)
data class _air_quality(
    val no2: Float,
    val pm2_5: Float,
    val pm10: Float,
    val us_epa_index: Int,
)
data class _condition(
    val text:String,
    val icon:String,
)