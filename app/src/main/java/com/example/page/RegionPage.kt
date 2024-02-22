package com.example.page

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.service.WeatherResponse
import com.example.widget.RegionAppBar
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionPage(navController: NavController, data:WeatherResponse){
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { RegionAppBar.build(navController = navController) },
        floatingActionButton = { AddRegionButton() }
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(60.dp))
                if (showDialog.value){
                    val lazyListState = rememberLazyListState()
                    val items = listOf("Taipei", "Taichung", "Tainan", "Taipei", "Taichung", "Tainan", "Taipei", "Taichung", "Tainan")
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        confirmButton = { /*TODO*/ },
                        title = { Text(text = "Add a region")},
                        text = {
                            LazyColumn(state = lazyListState, horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(5.dp)){
                                items(items = items) {
                                    Row(modifier = Modifier.clickable {
                                        showDialog.value = false

                                    }) {
                                        Text(text = it, fontSize = 20.sp,)
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Divider(modifier = Modifier.fillMaxWidth(0.95f))
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

private val showDialog = mutableStateOf(false)
@Composable
fun AddRegionButton(){
    FloatingActionButton(onClick = {  showDialog.value = true}
    )
    {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

/*

@Composable
fun DropdownDemo() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Taipei", "Taichung", "Tainan", "Taipei", "Taichung", "Tainan")
    var selectedIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement =  Arrangement.Center, Alignment.CenterHorizontally) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .align(Alignment.CenterHorizontally)
            .border(2.dp, Color.Black)
            .clickable(onClick = { expanded = true })
        ) {
            Text("Add Region", style = Typography.labelSmall)
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }

        if (expanded) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        onClick = {
                            selectedIndex = index
                            expanded = false
                        }, text = { Text(text = s) })
                }
            }
        }
    }
}
 */