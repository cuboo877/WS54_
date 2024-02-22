package com.example.page

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.service.WeatherResponse
import com.example.service.forecastDay
import com.example.service.hourData
import com.example.widget.HomeAppBar
import com.example.widget.NavDrawerContent
import com.example.ws54_compose_prac1.ui.theme.Averta
import com.example.ws54_compose_prac1.ui.theme.Typography
import com.example.ws54_compose_prac1.ui.theme.widgetLabelSmall
import kotlinx.coroutines.CoroutineScope
import java.time.LocalTime


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomePage(navController: NavHostController, data: WeatherResponse) {
    val lazyListState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                NavDrawerContent.build(
                    navController = navController,
                    scope = scope,
                    drawerState = drawerState
                )
            }
        }){
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = { HomeAppBar.build(region = data.location.region, scope = scope,drawerState = drawerState)},
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, top = 56.dp, 10.dp, 10.dp),
                state = lazyListState
            ) {
                item{
                    _buildCurrentTemp(data = data)
                    Spacer(modifier = Modifier.height(20.dp))
                    _buildMaxAndMinTemp(data = data)
                    Spacer(modifier = Modifier.height(20.dp))
                    _buildTempPerHour(data = data, scope)
                    Spacer(modifier = Modifier.height(20.dp))
                    _buildTempInTenDays(data = data)
                    Spacer(modifier = Modifier.height(20.dp))
                    _buildPM25_And_Uv(data = data)
                }
            }


        }
    }
}

//--------------------------------------------------------------------------------------

//現在溫度+icon
@Composable
fun _buildCurrentTemp(data: WeatherResponse){
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "${data.current.temp_c}°",
                fontSize = 80.sp,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = -8.sp,
                fontWeight = FontWeight.Light,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "${data.current.condition.text}",
                    modifier = Modifier.padding(start = 5.dp),
                    style = Typography.labelSmall
                )
            }
        }
        AsyncImage(
            model = "https:${data.current.condition.icon}",
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
        )
    }
}

// 今日最高最低溫度+體感溫度
@Composable
fun _buildMaxAndMinTemp(data: WeatherResponse){

    Text(text = "${data.forecast.forecastday[0].day.maxtemp_c.toString()}° / " +
            "${data.forecast.forecastday[0].day.mintemp_c.toString()}°  feels like " +
            "${data.current.feelslike_c}°",
        style = Typography.labelSmall,
        modifier = Modifier
            .padding(start = 5.dp)
            .padding(start = 20.dp)
    )
}

//--------------------------------------------------------------------------------------
//每小時溫度區
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun _buildTempPerHour(data: WeatherResponse, scope: CoroutineScope){
    val lazyListState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    val hoursData = data.forecast.forecastday[0].hour //list

    val coroutineScope = rememberCoroutineScope()
    var hourTime = LocalTime.now().hour
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .height(185.dp)
            .clip(RoundedCornerShape(size = 15.dp))
            .background(color = Color.Gray.copy(alpha = 0.5f))
            .padding(5.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
            ,horizontalArrangement = Arrangement.Center) {
            Text(text = "Hourly Weather", fontFamily = Averta, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
        }
        Divider(color = Color.White, modifier = Modifier
            .fillMaxWidth(0.92f)
            .padding(5.dp))
        LazyRow(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

            ,
            state = lazyListState,
            flingBehavior = snapFlingBehavior,

            ) {
            items(hoursData.size)
            {
                _buildPerHourData(data = hoursData[it])
            }
        }

        LaunchedEffect(scope){
            lazyListState.scrollToItem(hourTime)
        }
    }
}

// 每小時溫度小物件
@Composable
fun _buildPerHourData(data:hourData){
    Column(verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(5.dp)
    ) {
        Text(text = data.time.takeLast(5), fontSize = 15.sp, color = Color.White)
        AsyncImage(model = "https:${data.condition.icon}", contentDescription = null, modifier = Modifier.size(57.dp))
        Text(text = "${data.temp_c}°", fontFamily = Averta, color = Color.White, fontSize = 15.sp)
        Text(text = "${data.humidity}%", fontFamily = Averta, color = Color.White, fontSize = 15.sp)
    }
}

//--------------------------------------------------------------------------------------
//10天內天氣狀態區域
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun _buildTempInTenDays(data: WeatherResponse){
    val daysData = data.forecast.forecastday //List<forecastDay>
    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(color = Color.Gray.copy(alpha = 0.5f))
        .padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            Modifier
                .fillMaxWidth()
            ,horizontalArrangement = Arrangement.Center) {
            Text(text = "Weather In Ten Days", fontFamily = Averta, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
        }
        Divider(color = Color.White, modifier = Modifier
            .fillMaxWidth(0.92f)
            .padding(5.dp))
        daysData.forEach(){
            _buildTempInTenDaysData(data = it)
        }
    }
}

//10天內天氣狀態
@Composable
fun _buildTempInTenDaysData(data: forecastDay){
    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),) {
        Row(Modifier.width(80.dp)) {
            Text(
                text = data.date
                    .takeLast(5)
                    .replace("-","/"),
                style = widgetLabelSmall
            )
        }
        Row(
            Modifier.width(60.dp)
        ){
            Text(
                text = "${data.day.avghumidity}%",
                style = widgetLabelSmall
            )
        }

        AsyncImage(model = "https:${data.day.condition.icon}", contentDescription = null)
        Row(Modifier.width(60.dp)){
            Text(text = "${data.day.maxtemp_c}°", style = widgetLabelSmall)
        }
        Row(Modifier.width(60.dp)){
            Text(text = "${data.day.mintemp_c}°", style = widgetLabelSmall)
        }

    }
}

//--------------------------------------------------------------------------------------
//空氣品質
@Composable
fun _buildPM25_And_Uv(data: WeatherResponse){
    Row(Modifier.fillMaxWidth() ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(15.dp))
                .background(color = Color.Gray.copy(alpha = 0.5f))
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,)
        {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),horizontalArrangement = Arrangement.Center) {
                Text(text = "PM2.5", fontFamily = Averta, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
            }
            Divider(color = Color.White, modifier = Modifier.fillMaxWidth(0.92f))
            Text(data.current.air_quality.pm2_5.toString(),fontFamily = Averta, color = Color.White, fontSize = 40.sp,)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(color = Color.Gray.copy(alpha = 0.5f))
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,)
        {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),horizontalArrangement = Arrangement.Center) {
                Text(text = "UV", fontFamily = Averta, color = Color.White, fontSize = 15.sp, textAlign = TextAlign.Center)
            }
            Divider(color = Color.White, modifier = Modifier.fillMaxWidth(0.92f))
            Text(data.current.uv.toString(),fontFamily = Averta, color = Color.White, fontSize = 40.sp,)
        }
    }
}