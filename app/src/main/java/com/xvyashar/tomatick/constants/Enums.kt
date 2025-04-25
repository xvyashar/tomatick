package com.xvyashar.tomatick.constants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    Settings("Settings", Icons.Default.Settings)
}