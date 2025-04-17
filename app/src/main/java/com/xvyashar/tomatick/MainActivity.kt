package com.xvyashar.tomatick

import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.xvyashar.tomatick.ui.theme.TomatickTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xvyashar.tomatick.composables.BottomNavItem
import com.xvyashar.tomatick.composables.BottomNavigationBar
import com.xvyashar.tomatick.composables.screens.HomeScreen
import com.xvyashar.tomatick.composables.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatickTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Keep track of selected tab
    var selectedTab by remember { mutableStateOf(BottomNavItem.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar (
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        },
        topBar = {
            Row (
                modifier = Modifier.padding(start = 32.dp, top = 84.dp)
            ) {
                Text("TOMATICK", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        },
    ) {
        // Main content area
        Box(modifier = Modifier.padding(it)) {
            when (selectedTab) {
                BottomNavItem.Home -> HomeScreen()
                BottomNavItem.Settings -> SettingsScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TomatickTheme () {
        MainScreen()
    }
}