package com.example.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object RegionAppBar {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun build(navController: NavController){
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Region", fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null,modifier = Modifier.size(20.dp))
                }
            },
            navigationIcon = {
                IconButton(onClick = {navController.navigate("Home")}) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }
}